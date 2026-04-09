package sn.ec2lt.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("client", r -> r.path("/api/client/**").uri("lb://client"))
                .route("compte", r -> r.path("/api/compte/**").uri("lb://compte"))
                .build();
    }

}
