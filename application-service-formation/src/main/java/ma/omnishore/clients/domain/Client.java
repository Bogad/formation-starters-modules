package ma.omnishore.clients.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ma.co.omnidata.framework.services.datafiltering.annotations.DataFiltered;
import ma.co.omnidata.framework.services.lock.ILockable;
import ma.co.omnidata.framework.services.sequence.annotations.SequencedWith;
import ma.co.omnidata.framework.services.tracing.annotations.AttributeTrace;
import ma.co.omnidata.framework.services.tracing.annotations.Traceable;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@DataFiltered
@Traceable
public class Client implements Serializable, ILockable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@AttributeTrace(key = "lastName", label = "Nom")
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "first_name")
	@AttributeTrace(key = "firstName", label = "Prénom")
	private String firstName;
	@Column(name = "email")
	@AttributeTrace(key = "email", label = "Email")
	private String email;
	@Column(name = "id_number")
	@SequencedWith(code = "clientSeq")
	private String idNumber;
	@ManyToOne
	@AttributeTrace(key = "homeCountry", label = "Pays")
	private Country country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}	

	@Override
	@JsonIgnore
	public String getIdLockable() {
		return this.id.toString();
	}

	@Override
	@JsonIgnore
	public String getLockableType() {
		return this.getClass().getCanonicalName();
	}
}
