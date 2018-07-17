package ma.kriauto.rest.domain;

import java.util.List;

public class ContactCity {
	private String city;
	private List Contact;
	
	public ContactCity() {
		super();
	}

	public ContactCity(String city, List contact) {
		super();
		this.city = city;
		Contact = contact;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List getContact() {
		return Contact;
	}

	public void setContact(List contact) {
		Contact = contact;
	}

	@Override
	public String toString() {
		return "ContactCity [city=" + city + ", Contact=" + Contact + "]";
	}
}
