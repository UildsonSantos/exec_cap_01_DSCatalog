package com.devsuperior.exec01.mydscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.exec01.mydscatalog.dto.ClientDTO;
import com.devsuperior.exec01.mydscatalog.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService clientService;
	
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") 
			Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "12") 
			Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name")  
			String orderBy, 
			@RequestParam(value = "direction", defaultValue = "DESC") 
			String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ClientDTO> pageableList = clientService.findAllPaged(pageRequest);		
		return ResponseEntity.ok().body(pageableList);		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
		ClientDTO dtoElem = clientService.findById(id);
		return ResponseEntity.ok().body(dtoElem);
	}
	
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO clientdto){		
		ClientDTO dtoElem = clientService.insert(clientdto);		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoElem.getId()).toUri();		
		return ResponseEntity.created(uri).body(dtoElem);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> insert(@PathVariable Long id, @RequestBody ClientDTO clientdto){
		ClientDTO dtoElem = clientService.update(id, clientdto);
		return ResponseEntity.ok().body(dtoElem);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
