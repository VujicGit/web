package beans;

import java.awt.image.BufferedImage;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Apartment {
	
	private String id;
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
	private LocalTime checkInTime;
	private LocalTime checkoutTime;
	private ApartmentStatus apartmentStatus;
	private List<Amenities> amenities;
	private List<Reservation> reservation;
	
	
	public Apartment() {
		super();
	}

	public Apartment(String id, ApartmentType type, int numberOfRooms, int numberOfGuests, Location location,
			List<Date> datesForRent, List<Date> datesForIssue, Host host, List<Comment> comments,
			List<BufferedImage> images, double price, 
			ApartmentStatus apartmentStatus, List<Amenities> amenities, List<Reservation> reservation) {
		super();
		this.id = id;
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.datesForRent = datesForRent;
		this.datesForIssue = datesForIssue;
		this.host = host;
		this.comments = comments;
		this.images = images;
		this.price = price;
		this.checkInTime = checkInTime;
		this.checkoutTime = checkoutTime;
		this.apartmentStatus = apartmentStatus;
		this.amenities = amenities;
		this.reservation = reservation;
	}

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

	public LocalTime getCheckInDate() {
		return checkInTime;
	}

	public void setCheckInDate(LocalTime checkInDate) {
		this.checkInTime = checkInDate;
	}

	public LocalTime getCheckoutDate() {
		return checkoutTime;
	}

	public void setCheckoutDate(LocalTime checkoutDate) {
		this.checkoutTime = checkoutDate;
	}

	public ApartmentStatus getStatus() {
		return apartmentStatus;
	}

	public void setStatus(ApartmentStatus apartmentStatus) {
		this.apartmentStatus = apartmentStatus;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	

}
