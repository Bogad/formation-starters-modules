package ma.omnishore.clients.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.co.omnidata.framework.services.lock.exceptions.LockException;
import ma.omnishore.clients.domain.Client;
import ma.omnishore.clients.service.IClientService;

/**
 * Client controller.
 **/
@RestController
@RequestMapping("/api/client")
public class ClientController {

	@Autowired
	private IClientService clientService;

	private static final Logger log = LoggerFactory.getLogger(ClientController.class);

	// -------------------get All Clients---------------------------------------------
	@GetMapping
	@PreAuthorize("hasRole('client.read')")
	public ResponseEntity<List<Client>> findAll() {
		log.info("Returning client list from database.");
		List<Client> clients = clientService.getAllClients();
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}

	// -------------------Retrieve Single Client-----------------------------------------
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getClient(@PathVariable("id") long id) {
		Client client = clientService.getClient(id);
		return new ResponseEntity<>(client, HttpStatus.OK);
	}

	// -------------------Load Single Client-----------------------------------------
	@GetMapping(value = "/{id}/edit")
	public ResponseEntity<?> loadClient(@PathVariable("id") long id) throws LockException {
		Client client = new Client();
		client.setId(id);
		client = clientService.loadClient(client);
		return new ResponseEntity<>(client, HttpStatus.OK);
	}

	// -------------------Create a Client-------------------------------------------
	@PostMapping
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		client = clientService.createClient(client);
		return new ResponseEntity<>(client, HttpStatus.CREATED);
	}

	// ------------------- Update a Client ------------------------------------------------
	@PutMapping
	public ResponseEntity<?> updateClient(@RequestBody Client client) throws LockException{
		clientService.updateClient(client);
		return new ResponseEntity<>(client, HttpStatus.OK);
	}

	// ------------------- Delete a Client-----------------------------------------
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable("id") long id) {
		clientService.deleteClient(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
