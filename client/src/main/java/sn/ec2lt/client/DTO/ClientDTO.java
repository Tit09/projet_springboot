package sn.ec2lt.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ClientDTO {
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String adresse;
}
