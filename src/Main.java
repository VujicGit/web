import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import beans.SortByGuestsAscending;
import beans.SortByGuestsDescenging;
import beans.SortByPriceAscending;
import beans.SortByPriceDescending;
import beans.SortByRoomsAscending;
import beans.SortByRoomsDescending;
import beans.Status;
import beans.User;

public class Main {

	public static void main(String[] args) {
		

		/*Apartment amenity = new Amenities("1", "tv");
		
		HashMap<String, Amenities> mapUser = new HashMap<String, Amenities>();
		mapUser.put(amenity.getId(), amenity);

		ObjectMapper mapperUser = new ObjectMapper();

		
	
		
		try {
			mapperUser.writeValue(Paths.get("WebContent\\data\\amenities.json").toFile(), mapUser);
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
		/*try {
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
		*/
		ObjectMapper mapper2 = new ObjectMapper();
		HashMap<String, Apartment> map2 = null;
		TypeReference<HashMap<String, Apartment>> typeRef = new TypeReference<HashMap<String, Apartment>>() {
		};
		// convert JSON file to map
		try {
			map2 = mapper2.readValue(Paths.get("WebContent\\data\\apartments.json").toFile(), typeRef);
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
		
		Collection<Apartment> apartments =  map2.values();
		ArrayList<Apartment> apartmentList = new ArrayList<Apartment>(apartments);
		
		Collections.sort(apartmentList, new SortByGuestsDescenging());
		
		for(Apartment it : apartmentList) {
			System.out.println(it.getNumberOfGuests());
		}
		
		

	}

}