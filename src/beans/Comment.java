package beans;

public class Comment {

	private Guest guest;
	private Apartment apartment;
	private String content;
	private int rating;
	
	public Comment() {
		super();
	}

	public Comment(Guest guest, Apartment apartment, String content, int rating) {
		super();
		this.guest = guest;
		this.apartment = apartment;
		this.content = content;
		this.rating = rating;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
}
