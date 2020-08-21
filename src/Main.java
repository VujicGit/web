import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange.Map;

import beans.Address;
import beans.Amenities;
import beans.Apartment;
import beans.ApartmentStatus;
import beans.ApartmentType;
import beans.Comment;
import beans.Guest;
import beans.Host;
import beans.Location;
import beans.Reservation;
import beans.Status;
import dao.impl.ApartmentDAO;
import javafx.util.converter.LocalTimeStringConverter;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Address address = new Address("Milosa Crnjanskog", "8", "Sremska Mitrovica", "22000");
		Location location = new Location(32, 23, address);
		ArrayList<Date> datesForRent = new ArrayList<Date>();
		datesForRent.add(new Date(2020, 3, 28));
		ArrayList<Date> datesForIssue = new ArrayList<Date>();
		datesForIssue.add(new Date(2020, 4, 12));
		Host host = new Host();
		ArrayList<Comment> comments = new ArrayList<Comment>();
		comments.add(new Comment(new Guest(), new Apartment(), "dadada", 2));
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		LocalTime checkInTime = LocalTime.of(12, 30);
		LocalTime checkOutTime = LocalTime.of(8, 30);
		ArrayList<Amenities> amenities = new ArrayList<Amenities>();
		amenities.add(new Amenities("1", "Tv"));
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		reservations.add(new Reservation(new Apartment(), new Date(2020, 3, 12), 4, 22999, "dada", new Guest(), Status.ACEPTED));
		Apartment apartment = new Apartment("apar1", ApartmentType.FULL, 3, 3, location, datesForRent, datesForIssue, host, comments, images, 32,ApartmentStatus.ACTIVE, amenities, reservations);
		
		/*HashMap<String, Apartment> map = new HashMap<String, Apartment>();
		
		map.put("apar1", apartment);
		
		ObjectMapper mapper = new ObjectMapper();
		
	    // convert map to JSON file
	    try {
			mapper.writeValue(Paths.get("user.json").toFile(), map);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    ObjectMapper mapper2 = new ObjectMapper();
	    HashMap<String, Apartment> map2 = new HashMap<String, Apartment>();
	    TypeReference<HashMap<String,Apartment>> typeRef 
        = new TypeReference<HashMap<String,Apartment>>() {};
	    // convert JSON file to map
	    try {
			map2 = mapper2.readValue(Paths.get("user.json").toFile(), typeRef);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   System.out.println(map2.get("apar1").getNumberOfGuests());

	   */
		
		ApartmentDAO ad = new ApartmentDAO("F:\\Fakultet1\\Survival 3\\Semestar 6\\Web programiranje\\Projekat\\web\\WebContent");
		ArrayList<Apartment> apartmentList = new ArrayList<Apartment>(ad.findAll());
		for (Apartment apartment2 : apartmentList) {
			System.out.println(apartment2.getId());
		}
		
		Apartment apartment32 = new Apartment();
		apartment32.setId("apar2");
		System.out.println(ad.count());
		
		
		
	}

}
