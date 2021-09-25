package com.devsuperior.exec01.mydscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.exec01.mydscatalog.dto.ClientDTO;
import com.devsuperior.exec01.mydscatalog.entities.Client;
import com.devsuperior.exec01.mydscatalog.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		List<Client> list = clientRepository.findAll();
		return list.stream().map(elem -> new ClientDTO(elem)).collect(Collectors.toList());
	}
}
