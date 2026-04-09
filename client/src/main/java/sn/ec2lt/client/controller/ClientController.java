package sn.ec2lt.client.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ec2lt.client.DTO.ClientDTO;
import sn.ec2lt.client.entity.Client;
import sn.ec2lt.client.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> findAll(){
        return new ResponseEntity<>(this.clientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable int id){
        return new ResponseEntity<>(this.clientService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody ClientDTO clientDTO){

        return new ResponseEntity<>(this.clientService.createClient(clientDTO), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable int id, @RequestBody ClientDTO clientDTO){

        return new ResponseEntity<>(this.clientService.updateClient(clientDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        this.clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        return new ResponseEntity<>(this.clientService.countClients(), HttpStatus.OK);
    }
}
