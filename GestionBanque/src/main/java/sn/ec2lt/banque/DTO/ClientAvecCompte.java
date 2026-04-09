package sn.ec2lt.banque.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.ec2lt.banque.entite.Compte;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAvecCompte {
    private Compte compte;
    private ClientDTO client;
}
