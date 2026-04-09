package sn.ec2lt.banque.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private double solde;
    @Column(name = "type")
    private String typeCompte;
    @Column(name = "id_client")
    private int clientId;
    @Column(unique = true, nullable = false)
    private String numeroCompte;
}
