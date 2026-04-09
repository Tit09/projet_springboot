package sn.ec2lt.banque.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ec2lt.banque.DTO.ClientAvecCompte;
import sn.ec2lt.banque.DTO.CreationCompte;
import sn.ec2lt.banque.DTO.Depot;
import sn.ec2lt.banque.entite.Compte;
import sn.ec2lt.banque.services.CompteService;
import sn.ec2lt.banque.services.Implement.CompteServiceImplement;

import java.util.List;

@RestController
@RequestMapping("/compte")
public class CompteController {

    @Autowired
    private CompteServiceImplement compteService;

    @GetMapping
    public ResponseEntity<List<Compte>> findAll(){
        return new ResponseEntity<>(this.compteService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Compte> creationCompte ( @RequestBody CreationCompte creationCompte ){
        return new ResponseEntity<>(this.compteService.creationCompte(creationCompte), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/depot")
    public ResponseEntity<Compte> depot(@PathVariable int id, @RequestBody Depot montant){
        return new ResponseEntity<>(this.compteService.depot(id,montant.getMontant()), HttpStatus.OK);
    }

    @PutMapping("/{id}/retrait")
    public ResponseEntity<Compte> retrait(@PathVariable int id, @RequestBody Depot montant){
        return new ResponseEntity<>(this.compteService.retrait(id,montant.getMontant()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientAvecCompte> getClientAvecCompte(@PathVariable int id){
        return new ResponseEntity<>(this.compteService.getClientAvecCompte(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/with-client")
    public ResponseEntity<ClientAvecCompte> getCompteWithClient(@PathVariable int id){
        return new ResponseEntity<>(this.compteService.getClientAvecCompte(id), HttpStatus.OK);
    }

}
