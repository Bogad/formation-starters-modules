package ma.omnishore.clients.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.co.omnidata.framework.services.context.providers.IProvider;

@Configuration
public class ProviderConfiguration {

	@Bean
	public IProvider provider() {
		return new MyKeycloakProvider();
	}

}