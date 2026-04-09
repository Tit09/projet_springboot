package sn.ec2lt.client.services;

import sn.ec2lt.client.DTO.ClientDTO;
import sn.ec2lt.client.entity.Client;

import java.util.List;

public interface ClientService {
    public Client createClient(ClientDTO client);
    public List<Client> findAll();
    public Client updateClient(ClientDTO client, int id);
    public Client getById(int id);

    public void deleteClient(int id);
    public long countClients();
}
