package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

		if (ctx.getAttribute("apartmentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}

	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAmenity(AmenityDTO amenityDTO) {
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		String id = amenitiesDAO.generateId();
		System.out.println(id);
		if (amenitiesDAO.existsByName(amenityDTO.getName())) {
			String message = "{\"errorMessage\": \"Amenity already exists\"}";
			return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		
		amenitiesDAO.add(new Amenities(id, amenityDTO.getName()));
		amenitiesDAO.saveGeneratedId(id);
		return Response.ok().build();
	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Amenities> getAllAmenities() {
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		ArrayList<Amenities> amenities = new ArrayList<Amenities>();
		for (Amenities it : new ArrayList<Amenities>(amenitiesDAO.findAll())) {
			if (!it.isDeleted()) {
				amenities.add(it);
			}
		}
		return amenities;

	}

	@POST
	@Path("/remove/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeAmentity(@PathParam("id") String id) {
		System.out.println(id);
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		if (amenitiesDAO.existsById(id)) {
			amenitiesDAO.deleteById(id);
			Amenities amenity = amenitiesDAO.findById(id);
			ArrayList<Apartment> apartments = new ArrayList<Apartment>(apartmentDAO.findAll());
			for (Apartment apartmentIt : new ArrayList<Apartment>(apartments)) {
				ArrayList<Amenities> amenities = (ArrayList<Amenities>) apartmentIt.getAmenities();
				for (Amenities amenitiesIt : new ArrayList<Amenities>(amenities)) {
					if (amenitiesIt.getId().equals(amenity.getId())) {
						amenities.remove(amenity);
					}
				}
				apartmentDAO.update(apartmentIt);
			}
			return Response.ok().build();
		}

		String message = "{\"errorMessage\": \"Amenity already exists\"}";
		return Response.status(Response.Status.BAD_REQUEST).entity(message).build();

	}

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAmenity(AmenityDTO amenityDTO) {

		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		if(!amenitiesDAO.existsById(amenityDTO.getId())) {
			String message = "{\"errorMessage\": \"Amenity does not exists\"}";
			return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
		}

		if(amenitiesDAO.isDeleted(amenityDTO.getId())) {
			String message = "{\"errorMessage\": \"Amenity does not exists\"}";
			return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		
		if(amenitiesDAO.existsByName(amenityDTO.getName())) {
			String message = "{\"errorMessage\": \"Amenity already exists\"}";
			return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		
		Amenities amenity = amenitiesDAO.findById(amenityDTO.getId());
		amenity.setName(amenityDTO.getName());
		amenitiesDAO.update(amenity);
		return Response.ok().build();
	}
	
	private int generateId() {
		File f = new File("WebContent" + File.separator + "data" + File.separator + "amenitiesId.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(line);
		return 0;
	}

}
