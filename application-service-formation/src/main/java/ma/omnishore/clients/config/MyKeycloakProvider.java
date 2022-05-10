package ma.omnishore.clients.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ma.co.omnidata.framework.oauth2.KeycloakProvider;
import ma.co.omnidata.framework.services.context.IRole;
import ma.co.omnidata.framework.services.context.IUnit;
import ma.co.omnidata.framework.services.context.IUser;
import ma.co.omnidata.framework.services.context.impl.Role;
import ma.co.omnidata.framework.services.context.impl.Unit;
import ma.co.omnidata.framework.services.context.impl.User;
import ma.co.omnidata.framework.services.context.providers.IProvider;

@Component
public class MyKeycloakProvider implements IProvider {
	
	@Autowired
	private KeycloakProvider keycloakProvider;

	public String getConnectedUser() {
		return keycloakProvider.getConnectedUser();
	}

	public IUser getUser() {
		User user = new User();
		user.setLogin(getConnectedUser());
		return user;
	}

	public String getConnectedRole() {
		return keycloakProvider.getConnectedRole();
	}

	public IRole getRole() {
		Role role = new Role();
		role.setLabel(getConnectedRole());
		return role;
	}

	public String getConnectedUorg() {
		return keycloakProvider.getConnectedUorg();
	}

	public IUnit getUnit() {
		Unit unit = new Unit();
		unit.setName(getConnectedUorg());
		return unit;
	}
}
