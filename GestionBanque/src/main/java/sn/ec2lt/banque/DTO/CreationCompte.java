package sn.ec2lt.banque.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreationCompte {
    private String numeroCompte;
    private double soldeInitial;
    private String typeCompte;
    private int clientId;
}
