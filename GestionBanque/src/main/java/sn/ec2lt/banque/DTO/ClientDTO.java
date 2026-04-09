package sn.ec2lt.banque.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ClientDTO {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
}
