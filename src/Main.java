import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.osgi.framework.AdminPermission;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Address;

import beans.Amenities;
import beans.Apartment;
import beans.ApartmentStatus;
import beans.ApartmentType;
import beans.Comment;
import beans.Gender;
import beans.Guest;
import beans.Host;
import beans.Location;
import beans.Reservation;
import beans.Role;
import beans.Status;
import beans.User;

public class Main {

	public static void main(String[] args) {
		

		User user = new User("pera", "pera123", "Petar", "Petrovic", Gender.MALE, Role.ADMIN);
		
		HashMap<String, User> mapUser = new HashMap<String, User>();
		mapUser.put(user.getUsername(), user);

		ObjectMapper mapperUser = new ObjectMapper();

		
		Guest guest = new Guest(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getRole());
		HashMap<String, Guest> mapGuest = new HashMap<String, Guest>();
		mapGuest.put(guest.getUsername(), guest);
		
		ObjectMapper mapperGuest = new ObjectMapper();
		
		try {
			mapperGuest.writeValue(Paths.get("WebContent\\data\\guests.json").toFile(), mapGuest);
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		// convert map to JSON file
		try {
			mapperUser.writeValue(Paths.get("WebContent\\data\\users.json").toFile(), mapUser);
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
		HashMap<String, User> map2 = new HashMap<String, User>();
		TypeReference<HashMap<String, User>> typeRef = new TypeReference<HashMap<String, User>>() {
		};
		// convert JSON file to map
		try {
			map2 = mapper2.readValue(Paths.get("WebContent\\data\\users.json").toFile(), typeRef);
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
		System.out.println(map2.get("pera").getName());
		

	}

}
