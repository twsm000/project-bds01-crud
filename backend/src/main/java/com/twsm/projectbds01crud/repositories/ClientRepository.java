package com.twsm.projectbds01crud.repositories;

import com.twsm.projectbds01crud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
