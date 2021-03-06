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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Apartment.class)

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
	private List<String> images;
	private double price;
	private String checkInTime;
	private String checkoutTime;
	private ApartmentStatus apartmentStatus;
	private List<Amenities> amenities;
	private List<Reservation> reservation;
	private boolean deleted;

	public Apartment() {
		super();
	}

	public Apartment(String id, ApartmentType type, int numberOfRooms, int numberOfGuests, Location location,
			List<Date> datesForRent, List<Date> datesForIssue, Host host, List<Comment> comments,
			List<String> images, double price, 
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

		this.deleted = false;

	}

	public Apartment(Apartment apartment) {
		this(apartment.getId(), apartment.getType(), apartment.getNumberOfRooms(), apartment.getNumberOfGuests(),
				apartment.getLocation(), apartment.getDatesForRent(), apartment.getDatesForIssue(), apartment.getHost(),
				apartment.getComments(), apartment.getImages(), apartment.getPrice(), apartment.getStatus(),
				apartment.getAmenities(), apartment.getReservation());
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

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
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


	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	
	
	
	

}
