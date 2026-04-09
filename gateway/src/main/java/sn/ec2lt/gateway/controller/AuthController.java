package sn.ec2lt.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import sn.ec2lt.gateway.dto.LoginRequest;
import sn.ec2lt.gateway.dto.LoginResponse;
import sn.ec2lt.gateway.security.JwtUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    // Utilisateurs en mémoire (pour l'examen)
    private static final Map<String, String> USERS = Map.of(
            "admin", "admin123",
            "user",  "user123"
    );

    private static final Map<String, List<String>> ROLES = Map.of(
            "admin", List.of("ADMIN"),
            "user",  List.of("USER")
    );

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody LoginRequest request) {
        String password = USERS.get(request.getUsername());

        if (password == null || !password.equals(request.getPassword())) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        List<String> roles = ROLES.get(request.getUsername());
        String token = jwtUtil.generateToken(request.getUsername(), roles);

        return Mono.just(ResponseEntity.ok(
                new LoginResponse(token, request.getUsername(), roles)
        ));
    }
}
