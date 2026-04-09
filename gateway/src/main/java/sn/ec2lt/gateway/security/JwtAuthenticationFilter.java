package sn.ec2lt.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    private static final List<String> PUBLIC_PATHS = List.of("/auth/login");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path   = exchange.getRequest().getPath().value();
        HttpMethod method = exchange.getRequest().getMethod();

        // 1. CORS preflight → laisser passer
        if (HttpMethod.OPTIONS.equals(method)) {
            return chain.filter(exchange);
        }

        // 2. Chemins publics → laisser passer
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        // 3. Vérifier le token
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 4. Vérifier les rôles selon la méthode et le chemin
        List<String> roles = jwtUtil.extractRoles(token);

        if (!hasAccess(path, method, roles)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // 5. Token valide et rôle OK → laisser passer
        return chain.filter(exchange);
    }

    private boolean hasAccess(String path, HttpMethod method, List<String> roles) {
        boolean isAdmin = roles.contains("ADMIN");
        boolean isUser  = roles.contains("USER") || isAdmin;

        // Clients
        if (path.startsWith("/api/client")) {
            if (HttpMethod.GET.equals(method))    return isUser;   // ADMIN + USER
            if (HttpMethod.POST.equals(method))   return isAdmin;  // ADMIN only
            if (HttpMethod.PUT.equals(method))    return isAdmin;  // ADMIN only
            if (HttpMethod.DELETE.equals(method)) return isAdmin;  // ADMIN only
        }

        // Comptes
        if (path.startsWith("/api/compte")) {
            if (HttpMethod.GET.equals(method))    return isUser;   // ADMIN + USER
            if (HttpMethod.PUT.equals(method))    return isUser;   // dépôt/retrait : ADMIN + USER
            if (HttpMethod.POST.equals(method))   return isAdmin;  // création compte : ADMIN only
            if (HttpMethod.DELETE.equals(method)) return isAdmin;  // ADMIN only
        }

        return isAdmin; // par défaut : ADMIN seulement
    }
}
