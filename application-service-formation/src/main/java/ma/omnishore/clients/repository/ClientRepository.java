package ma.omnishore.clients.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.omnishore.clients.domain.Client;

/**
 * Spring Data repository for the Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	List<Client> findByFirstName(String firstName);

}
