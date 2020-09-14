package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Amenities;
import beans.Apartment;
import beans.Status;
import dao.impl.AmenitiesDAO;
import dao.impl.ApartmentDAO;
import dao.impl.GuestDAO;
import dto.AmenityDTO;

@Path("/amenities")
public class AmenitiesService {
	
	@Context
	ServletContext ctx;

	@Context
	HttpServletRequest request;
	
	public AmenitiesService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("amenitiesDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("amenitiesDAO", new AmenitiesDAO(contextPath));
		}
		
		if(ctx.getAttribute("apartmentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
		
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAmenity(AmenityDTO amenityDTO) {
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
	
		if(amenitiesDAO.existsById(amenityDTO.getId())) {
			String message = "{\"errorMessage\": \"Amenity already exists\"}";
			return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		
		amenitiesDAO.add(new Amenities(amenityDTO.getId(), amenityDTO.getName()));
		return Response.ok().build();
	}
	
	@POST
	@Path("/remove")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeAmenity(AmenityDTO amenityDTO) {
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
		
		if(amenitiesDAO.deleteById(amenityDTO.getId())) {
			ArrayList<Apartment> apartments = new ArrayList(apartmentDAO.findAll());
			for (Apartment apartment : apartments) {
				ArrayList<Amenities> amenities = (ArrayList<Amenities>) apartment.getAmenities();
				for (Amenities amenity : amenities) {
					if(amenity.getId().equals(amenityDTO.getId())) {
						amenities.remove(amenity);
					}
				}
				apartmentDAO.update(apartment);
			}
			
			return Response.ok().build();
		}
		
		String message = "{\"errorMessage\": \"Can not remove amenity\"}";
		return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAmenity(AmenityDTO amenityDTO) {
		
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		Amenities amenity = new Amenities(amenityDTO.getId(), amenityDTO.getName());
		if(amenitiesDAO.update(amenity)) {
			return Response.ok().build();
		}
		
		String message = "{\"errorMessage\": \"Can not update amenity\"}";
		return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
	}
}
