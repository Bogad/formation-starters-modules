package ma.omnishore.clients.service;

import java.util.List;

import ma.co.omnidata.framework.services.lock.exceptions.LockException;
import ma.omnishore.clients.domain.Client;

public interface IClientService {

	/**
	 * Save a client.
	 * @param client the entity to save.
	 * @return the persisted entity.
	 */
	Client createClient(Client client);

	/**
	 * Get all the clients.
	 * @return the list of entities.
	 */
	List<Client> getAllClients();

	/**
	 * Get one client by id.
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Client getClient(Long id);

	/**
	 * Update a client.
	 * @param client  the entity to update.
	 * @return the updated entity.
	 * @throws LockException 
	 */
	public Client updateClient(Client client) throws LockException;

	/**
	 * Delete the client by id.
	 * @param id the id of the entity.
	 */
	void deleteClient(Long id);

	/**
	 * Load one client by id.
	 * 
	 * @param id the id of the entity.
	 * @return the entity.
	 * @throws LockException 
	 */
	Client loadClient(Client client) throws LockException;
}
