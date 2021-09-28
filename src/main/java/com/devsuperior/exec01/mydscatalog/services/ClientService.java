package com.devsuperior.exec01.mydscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.exec01.mydscatalog.dto.ClientDTO;
import com.devsuperior.exec01.mydscatalog.entities.Client;
import com.devsuperior.exec01.mydscatalog.repositories.ClientRepository;
import com.devsuperior.exec01.mydscatalog.services.exceptions.DatabaseException;
import com.devsuperior.exec01.mydscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> pageableList = clientRepository.findAll(pageRequest);
		return pageableList.map(elem -> new ClientDTO(elem));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> optClient = clientRepository.findById(id);
		Client entity = optClient.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO clientDTO) {
		Client entity = new Client();
		copyDTOToEntity(clientDTO, entity);			
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDTO) {
		try {
			Client entity = clientRepository.getOne(id);
			copyDTOToEntity(clientDTO, entity);
			entity = clientRepository.save(entity);
			return new ClientDTO(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " +id);
		}		
	}
	
	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not Found " +id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}		
	}
	
	private void copyDTOToEntity(ClientDTO clientDTO, Client entity) {
		entity.setName(clientDTO.getName());
		entity.setCpf(clientDTO.getCpf());
		entity.setIncome(clientDTO.getIncome());
		entity.setBirthDate(clientDTO.getBirthDate());
		entity.setChildren(clientDTO.getChildren());			
	}
}
