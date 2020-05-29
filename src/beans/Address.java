package beans;

public class Address {

	private String street;
	private String number;
	private String place;
	private String zipCode;
	
	public Address() {
		this.street = "";
		this.number = "";
		this.place = "";
		this.zipCode = "";
	}

	public Address(String street, String number, String place, String zipCode) {
		super();
		this.street = street;
		this.number = number;
		this.place = place;
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return this.street + " " + this.number + ", " + this.place + " " + this.zipCode;
	}
	
	

	
	
	
}
