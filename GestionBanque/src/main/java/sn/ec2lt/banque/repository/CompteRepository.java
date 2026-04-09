package sn.ec2lt.banque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.ec2lt.banque.entite.Compte;

public interface CompteRepository extends JpaRepository <Compte, Integer> {
    @Query("select c from Compte c where c.numeroCompte = :num")
    Compte findCompteByNumeroCompte(@Param("num") String numeroCompte);
}
