package sn.ec2lt.banque.services;
import sn.ec2lt.banque.DTO.ClientAvecCompte;
import sn.ec2lt.banque.DTO.CreationCompte;
import sn.ec2lt.banque.entite.Compte;

import java.util.List;

public interface CompteService {
    Compte creationCompte(CreationCompte creationCompte);
    Compte getCompteById(int id);
    Compte depot(int id, double montant);
    Compte retrait(int id, double montant);
    List<Compte>findAll();

    ClientAvecCompte getClientAvecCompte(int id);
}
