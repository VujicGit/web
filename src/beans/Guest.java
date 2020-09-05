package beans;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User {
	private List<Apartment> apartments;
	private List<Reservation> reservations;
	
	public Guest() {
		super();
	}

	public Guest(List<Apartment> apartments, List<Reservation> reservations) {
		super();
		this.apartments = apartments;
		this.reservations = reservations;
	}
	
	public Guest(String username, String password, String name, String surname, Gender gender, Role role) {
		super(username, password, name, surname, gender, role);
		this.apartments = new ArrayList<Apartment>();
		this.reservations = new ArrayList<Reservation>();
	}

	public List<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}


	
	
}
