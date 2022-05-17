package ma.omnishore.clients.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.co.omnidata.framework.services.lock.annotations.ItaroneLock;
import ma.co.omnidata.framework.services.lock.annotations.ItaroneUnlock;
import ma.co.omnidata.framework.services.lock.exceptions.LockException;
import ma.co.omnidata.framework.services.tracing.annotations.MethodTrace;
import ma.omnishore.clients.domain.Client;
import ma.omnishore.clients.repository.ClientRepository;
import ma.omnishore.clients.service.IClientService;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService implements IClientService {

	private final Logger log = LoggerFactory.getLogger(ClientService.class);

	private final ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	/**
	 * Save a client.
	 * 
	 * @param client the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	@MethodTrace(key = "client.create", label = "Création Client")
	public Client createClient(Client client) {
		log.debug("Request to save Client : {}", client);
		return clientRepository.save(client);
	}

	/**
	 * Get all the clients.
	 * 
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Client> getAllClients() {
		log.debug("Request to get all Clients");
		return StreamSupport.stream(clientRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	/**
	 * Get one client by id.
	 * 
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Client getClient(Long id) {
		log.debug("Request to get Client : {}", id);
		return clientRepository.findById(id).orElse(null);
	}
	

	/**
	 * Load one client by id.
	 * 
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@ItaroneLock
	public Client loadClient(Client client) throws LockException{
		log.debug("Loading Client with id: {}", client.getId());
		return clientRepository.findById(client.getId()).orElse(null);
	}


	/**
	 * Update a client.
	 * 
	 * @param client the entity to update.
	 * @return the updated entity.
	 */
	@Override
	@ItaroneUnlock
	@MethodTrace(key = "client.update", label = "Modification Client", mode = "edit")
	public Client updateClient(Client client) throws LockException{
		log.debug("Request to update Client : {}", client);
		if (!clientRepository.existsById(client.getId())) {
			return null;
		}
		return clientRepository.save(client);
	}

	/**
	 * Delete the client by id.
	 * 
	 * @param id the id of the entity.
	 */
	@Override
	public void deleteClient(Long id) {
		log.debug("Request to delete Client : {}", id);
		clientRepository.deleteById(id);
	}
}
