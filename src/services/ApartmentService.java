package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Address;
import beans.Apartment;
import beans.Location;
import beans.Reservation;
import dao.impl.ApartmentDAO;
import dto.SearchDTO;

@Path("/apartments")
public class ApartmentService {
	
	@Context
	ServletContext ctx;
	
	public ApartmentService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("apartmentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getAllApartments() {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> search(SearchDTO searchDTO) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		ArrayList<Apartment> apartments = new ArrayList<Apartment>(dao.findAll());
		for (Apartment apartment : new ArrayList<Apartment>(apartments)) {
			if(!equalsByPlace(apartment, searchDTO) && !searchDTO.getLocation().equals("")) {
				apartments.remove(apartment);
			}
			
			if(!checkPrice(apartment, searchDTO) && searchDTO.getMinPrice() != 0.0 && searchDTO.getMaxPrice() != 0.0) {
				apartments.remove(apartment);
			}
			
			if(!checkRooms(apartment, searchDTO) && searchDTO.getNumberOfRooms() != 0) {
				apartments.remove(apartment);
			}
			
			if(!checkGuests(apartment, searchDTO) && searchDTO.getNumberOfGuests() != 0) {
				apartments.remove(apartment);
			}
			
			
		}
		
			
		
		return apartments;
	}
	
	@GET
	@Path("/getApartment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment getApartment(@PathParam("id") String id) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findById(id);
	}
	
	/*Methods used for search*/
	private String getPlace(Apartment apartment) {
		Location apartmentLocation = apartment.getLocation();
		Address apartmentAddress = apartmentLocation.getAddress();
		String place = apartmentAddress.getPlace();
		return place;
	}
	
	private boolean equalsByPlace(Apartment apartment, SearchDTO searchDTO) {
		return getPlace(apartment).toLowerCase().equals(searchDTO.getLocation().toLowerCase());
	}
	
	private boolean checkPrice(Apartment apartment, SearchDTO searchDTO) {
		if(apartment.getPrice() >= searchDTO.getMinPrice() && apartment.getPrice() <= searchDTO.getMaxPrice()) {
			return true;
		}
		return false;
	}
	
	private boolean checkRooms(Apartment apartment, SearchDTO searchDTO) {
		if(apartment.getNumberOfRooms() == searchDTO.getNumberOfRooms()) {
			return true;
		}
		return false;
	}
	
	private boolean checkGuests(Apartment apartment, SearchDTO searchDTO) {
		if(apartment.getNumberOfGuests() == searchDTO.getNumberOfGuests()) {
			return true;
		}
		return false;
	}
	
	
}