package beans;

import java.util.Date;

public class Reservation {
	
	private Apartment apartment;
	private Date reservationBegins;
	private int numberOfNights;
	private double price;
	private String message;
	private Guest guest;
	private Status status;
	private boolean deleted;
	
	public Reservation() {
		super();
	}
	
	public Reservation(Apartment apartment, Date reservationBegins, int numberOfNights, double price, String message,
			Guest guest, Status status) {
		super();
		this.apartment = apartment;
		this.reservationBegins = reservationBegins;
		this.numberOfNights = numberOfNights;
		this.price = price;
		this.message = message;
		this.guest = guest;
		this.status = status;
		this.deleted = false;
	}


	public Apartment getApartment() {
		return apartment;
	}


	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}


	public Date getReservationBegins() {
		return reservationBegins;
	}


	public void setReservationBegins(Date reservationBegins) {
		this.reservationBegins = reservationBegins;
	}


	public int getNumberOfNights() {
		return numberOfNights;
	}


	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Guest getGuest() {
		return guest;
	}


	public void setGuest(Guest guest) {
		this.guest = guest;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	

}
