package ma.omnishore.clients.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import ma.co.omnidata.framework.services.datafiltering.repositories.ItaroneDataFilteringRepository;
import ma.omnishore.clients.domain.Client;

/**
 * Spring Data repository for the Client entity.
 */
@Repository
public interface ClientRepository extends ItaroneDataFilteringRepository<Client, Long> {
	
	List<Client> findByFirstName(String firstName);
}
