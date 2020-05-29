package beans;

import java.awt.image.BufferedImage;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Apartment {
	
	private ApartmentType type;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private List<Date> datesForRent;
	private List<Date> datesForIssue;
	private Host host;
	private List<Comment> comments;
	private List<BufferedImage> images;
	private double price;
	private Date checkInDate;
	private String checkoutDate;
	private Status status;
	private List<Amenities> amenities;
	private List<Reservation> reservation;
	
	
	public ApartmentType getType() {
		return type;
	}

	public void setType(ApartmentType type) {
		this.type = type;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Date> getDatesForRent() {
		return datesForRent;
	}

	public void setDatesForRent(List<Date> datesForRent) {
		this.datesForRent = datesForRent;
	}

	public List<Date> getDatesForIssue() {
		return datesForIssue;
	}

	public void setDatesForIssue(List<Date> datesForIssue) {
		this.datesForIssue = datesForIssue;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<BufferedImage> getImages() {
		return images;
	}

	public void setImages(List<BufferedImage> images) {
		this.images = images;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Amenities> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenities> amenities) {
		this.amenities = amenities;
	}

	public List<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}
	
	
	
	
	

}
