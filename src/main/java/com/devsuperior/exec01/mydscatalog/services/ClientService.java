package com.devsuperior.exec01.mydscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.exec01.mydscatalog.entities.Client;
import com.devsuperior.exec01.mydscatalog.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
}
