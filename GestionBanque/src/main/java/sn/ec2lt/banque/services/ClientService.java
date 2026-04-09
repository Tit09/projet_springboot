package sn.ec2lt.banque.services;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sn.ec2lt.banque.DTO.ClientDTO;

@Hidden
@FeignClient(name = "client")
public interface ClientService {

    @GetMapping("/api/client/{id}")
    ClientDTO getClient(@PathVariable("id") int id);


}
