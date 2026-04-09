package sn.ec2lt.banque.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacMessage {
    private String numcompte;
    private String type;
    private Double montant;
    private Double solde;

    private int idClient;
}
