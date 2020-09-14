package beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Guest.class)
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
	
	public Guest(Guest guest) {
		this(guest.getApartments(), guest.getReservations());
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
