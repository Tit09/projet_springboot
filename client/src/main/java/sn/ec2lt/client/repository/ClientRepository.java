package sn.ec2lt.client.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ec2lt.client.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
