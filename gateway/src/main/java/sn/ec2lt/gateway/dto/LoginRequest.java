package sn.ec2lt.gateway.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
