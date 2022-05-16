package ma.omnishore.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ma.co.omnidata.framework.services.datafiltering.annotations.EnableItaroneDataFiltering;
import ma.co.omnidata.framework.services.sequence.annotations.EnableSequenceGenerator;

@SpringBootApplication
@EnableItaroneDataFiltering
@EnableSequenceGenerator
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}
}
