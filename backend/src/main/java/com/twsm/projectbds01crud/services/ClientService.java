package com.twsm.projectbds01crud.services;

import com.twsm.projectbds01crud.dto.ClientDTO;
import com.twsm.projectbds01crud.entities.Client;
import com.twsm.projectbds01crud.repositories.ClientRepository;
import com.twsm.projectbds01crud.services.exceptions.DatabaseException;
import com.twsm.projectbds01crud.services.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest)
                .map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new)
                .orElseThrow(() -> new ResourceNotFound("Entity not found"));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        DTOToClientHelper.copy(dto, entity);
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = clientRepository.getOne(id);
            DTOToClientHelper.copy(dto, entity);
            return new ClientDTO(clientRepository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(String.format("Id not found %d", id));
        }
    }

    public void delete(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFound(String.format("Id not found %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private static class DTOToClientHelper {
        private static void copy(ClientDTO dto, Client entity) {
            entity.setBirthDate(dto.getBirthDate());
            entity.setChildren(dto.getChildren());
            entity.setCpf(dto.getCpf());
            entity.setIncome(dto.getIncome());
            entity.setName(dto.getName());
        }
    }
}
