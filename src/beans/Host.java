package beans;

import java.util.List;

public class Host extends User {
	private List<Apartment> apartments;

	public Host(List<Apartment> apartments) {
		super();
		this.apartments = apartments;
	}

	public Host() {
		super();
	}

	public List<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}
}
