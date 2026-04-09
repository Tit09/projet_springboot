package sn.ec2lt.client.services.Implements;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ec2lt.client.DTO.ClientDTO;
import sn.ec2lt.client.entity.Client;
import sn.ec2lt.client.repository.ClientRepository;
import sn.ec2lt.client.services.ClientService;

import java.util.List;

@Service

public class ClientServiceImplements implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Override
    public Client createClient(ClientDTO client) {


        Client clientsave = new Client();

        clientsave.setNom(client.getNom());
        clientsave.setPrenom(client.getPrenom());
        clientsave.setEmail(client.getEmail());
        clientsave.setPhone(client.getPhone());
        clientsave.setAdresse(client.getAdresse());
        return this.clientRepository.save(clientsave);
    }

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client updateClient(ClientDTO client, int id) {
        Client clientsave = new Client();

        clientsave.setId(id);
        clientsave.setNom(client.getNom());
        clientsave.setPrenom(client.getPrenom());
        clientsave.setEmail(client.getEmail());
        clientsave.setPhone(client.getPhone());
        clientsave.setAdresse(client.getAdresse());
        return this.clientRepository.save(clientsave);
    }

    @Override
    public Client getById( int id) {

        Client client = this.clientRepository.findById(id).orElse(null);
        return client;
    }

    @Override
    public void deleteClient(int id) {
        this.clientRepository.deleteById(id);
    }

    @Override
    public long countClients() {
        return this.clientRepository.count();
    }
}
