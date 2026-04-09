package sn.ec2lt.banque.services.Implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ec2lt.banque.DTO.ClientAvecCompte;
import sn.ec2lt.banque.DTO.ClientDTO;
import sn.ec2lt.banque.DTO.CreationCompte;
import sn.ec2lt.banque.DTO.TransacMessage;
import sn.ec2lt.banque.entite.Compte;
import sn.ec2lt.banque.exception.CompteInexistant;
import sn.ec2lt.banque.exception.MontantInvalide;
import sn.ec2lt.banque.exception.SoldeInsuffisantException;
import sn.ec2lt.banque.repository.CompteRepository;
import sn.ec2lt.banque.services.ClientService;
import sn.ec2lt.banque.services.CompteService;
import sn.ec2lt.banque.services.RabbitMQProducer;

import java.util.List;


@Service
public class CompteServiceImplement implements CompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientService clientService;


    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Override
    public Compte creationCompte(CreationCompte creationCompte) {
        if (creationCompte.getSoldeInitial() < 0){
            throw new MontantInvalide("le montant ne doit pas être inferieur à zéro ");
        }
        Compte compte = new Compte();
        compte.setNumeroCompte(creationCompte.getNumeroCompte());
        compte.setTypeCompte(creationCompte.getTypeCompte());
        compte.setSolde(creationCompte.getSoldeInitial());
        compte.setClientId(creationCompte.getClientId());
        return compteRepository.save(compte);
    }

    @Override
    public Compte getCompteById(int id) {

        Compte compte = this.compteRepository.getById(id);
        if (compte.getNumeroCompte() == null){
            throw new CompteInexistant("Ce compte n'existe pas");
        }
        return compte;
    }

    @Override
    public Compte depot(int id, double montant) {

        Compte compte = this.compteRepository.getById(id);
        compte.setSolde(compte.getSolde()+montant);

        Compte compteEnregistrer = this.compteRepository.save(compte);

        TransacMessage transacMessage = new TransacMessage(
                compteEnregistrer.getNumeroCompte(),
                "DEPOT",
                montant,
                compteEnregistrer.getSolde(),
                compteEnregistrer.getClientId()
        );
        this.rabbitMQProducer.sendTransactionMessage(transacMessage);
        return compteEnregistrer;
    }

    @Override
    public Compte retrait(int id, double montant) {

        Compte compte = this.compteRepository.getById(id);
        if (compte.getNumeroCompte() == null){
            throw new CompteInexistant("Ce compte n'existe pas");
        }
        if (compte.getSolde() < montant){
            throw new SoldeInsuffisantException("Votre solde est insuffisant");
        }

        compte.setSolde(compte.getSolde()-montant);

        Compte compteEnregistrer = this.compteRepository.save(compte);

        TransacMessage transacMessage = new TransacMessage(
                compteEnregistrer.getNumeroCompte(),
                "RETRAIT",
                montant,
                compteEnregistrer.getSolde(),
                compteEnregistrer.getClientId()
        );

        this.rabbitMQProducer.sendTransactionMessage(transacMessage);
        return compteEnregistrer;
    }

    @Override
    public List<Compte> findAll() {
        return compteRepository.findAll();
    }

    @Override
    public ClientAvecCompte getClientAvecCompte(int id) {
        Compte compte = this.compteRepository.findById(id).orElse(null);
        if (compte.getNumeroCompte() == null){
            throw new CompteInexistant("Ce compte n'existe pas");
        }

        ClientDTO clientDTO = this.clientService.getClient(compte.getClientId());

        return new ClientAvecCompte(compte,clientDTO);
    }
}
