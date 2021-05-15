package com.twsm.projectbds01crud.services;

import com.twsm.projectbds01crud.dto.ClientDTO;
import com.twsm.projectbds01crud.repositories.ClientRepository;
import com.twsm.projectbds01crud.services.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest)
                .map(ClientDTO::new);
    }


    public ClientDTO findById(Long id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new)
                .orElseThrow(() -> new ResourceNotFound("Entity not found"));
    }
}
