package com.devsuperior.exec01.mydscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.exec01.mydscatalog.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
