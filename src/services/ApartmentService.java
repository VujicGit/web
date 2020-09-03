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
		System.out.println("dadada");
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
		if(searchDTO.getLocation().equals("")) {
			return apartments;
		}
		for (Apartment apartment : new ArrayList<Apartment>(apartments)) {
			Location location = apartment.getLocation();
			Address address = location.getAddress();
			String place = address.getPlace();
			
			ArrayList<Date> datesForRent = (ArrayList<Date>) apartment.getDatesForRent();
			
			if(!place.toLowerCase().equals(searchDTO.getLocation().toLowerCase())) {
				apartments.remove(apartment);
			}
		
				
			
			
		}
		
			
		
		return apartments;
	}
	
	
}