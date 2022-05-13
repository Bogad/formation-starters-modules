package ma.omnishore.clients.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import ma.co.omnidata.framework.services.context.providers.IProvider;

@Configuration
public class ProviderConfiguration {

	@Bean
	@Primary
	public IProvider provider() {
		return new MyKeycloakProvider();
	}

}