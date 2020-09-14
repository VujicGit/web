package services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import beans.Address;
import beans.Apartment;
import beans.Guest;
import beans.Location;
import beans.Reservation;
import dao.impl.ApartmentDAO;
import dao.impl.GuestDAO;
import dto.ReservationDTO;
import dto.SearchDTO;

@Path("/apartments")
public class ApartmentService {

	@Context
	ServletContext ctx;

	@Context
	HttpServletRequest request;

	public ApartmentService() {

	}

	@PostConstruct
	public void init() {
		if (ctx.getAttribute("apartmentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
		if (ctx.getAttribute("guestDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("guestDAO", new GuestDAO(contextPath));
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
			if (!equalsByPlace(apartment, searchDTO) && !searchDTO.getLocation().equals("")) {
				apartments.remove(apartment);
			}

			if (!checkPrice(apartment, searchDTO) && searchDTO.getMinPrice() != 0.0 && searchDTO.getMaxPrice() != 0.0) {
				apartments.remove(apartment);
			}

			if (!checkRooms(apartment, searchDTO) && searchDTO.getNumberOfRooms() != 0) {
				apartments.remove(apartment);
			}

			if (!checkGuests(apartment, searchDTO) && searchDTO.getNumberOfGuests() != 0) {
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

	@GET
	@Path("/datesForIssue/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Date> datesForIssue(@PathParam("id") String id) {

		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");

		Apartment apartment = apartmentDAO.findById(id);

		ArrayList<Date> dates = (ArrayList<Date>) apartment.getDatesForIssue();

		return dates;

	}

	@POST
	@Path("/submitReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response submitReservation(ReservationDTO reservationDTO) {
		String apartmentId = reservationDTO.getId();
		Date startDate = reservationDTO.getStartDate();

		int nights = reservationDTO.getNights();

		ArrayList<Date> dates = (ArrayList<Date>) createDatesSequence(startDate, nights);
		/*
		 * System.out.println("Sekvenca datuma: \n"); for (Date date : dates) {
		 * System.out.println(date + "\n"); }
		 * System.out.println("----------------------------------");
		 */

		if (checkAvailability(apartmentId, dates)) {
			ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
			GuestDAO guestDAO = (GuestDAO) ctx.getAttribute("guestDAO");

			Apartment apartment = apartmentDAO.findById(apartmentId);

			double price = apartment.getPrice() * nights;
			String message = reservationDTO.getMessage();
			Guest guest = getLoggedInGuest();

			Reservation reservation = new Reservation(apartment, startDate, nights, price, message, guest,
					beans.Status.CREATED);
			


			apartment.getReservation().add(reservation);
			apartment.getDatesForIssue().removeAll(dates);
			guest.getReservations().add(reservation);

			apartmentDAO.update(apartment);
			guestDAO.update(guest);
			
			

			return Response.ok().build();
		}
		String message = "{\"hello\": \"This is a JSON response\"}";
		return Response.status(Status.BAD_REQUEST).entity(message).type(MediaType.APPLICATION_JSON).build();

	}

	private Guest getLoggedInGuest() {
		HttpSession session = request.getSession();
		Guest guest = (Guest) session.getAttribute("loggedInUser");
		return guest;
	}

	/* Meghods used for creating reservation */
	private List<Date> createDatesSequence(Date startDate, Date endDate) {

		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		while (calendar.getTime().before(endDate)) {

			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}

		return dates;
	}

	private List<Date> createDatesSequence(Date startDate, int nights) {

		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		Calendar endDateGenerator = new GregorianCalendar();
		endDateGenerator.setTime(startDate);
		endDateGenerator.add(endDateGenerator.DATE, nights + 1);

		Date endDate = endDateGenerator.getTime();

		while (calendar.getTime().before(endDate)) {

			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}

		return dates;

	}

	private boolean checkAvailability(String id, ArrayList<Date> dates) {

		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");

		Apartment apartment = apartmentDAO.findById(id);

		ArrayList<Date> datesForIssue = (ArrayList<Date>) apartment.getDatesForIssue();
		if (dates.size() > datesForIssue.size()) {
			return false;
		}

		/*if (isSubarray(datesForIssue, dates)) {
			return true;
		}*/
		
		for (Date date : dates) {
			if(!datesForIssue.contains(date)) {
				return false;
			}
		}
		return true;

	}

	private boolean isSubarray(List<Date> datesForIssue, List<Date> datesForRent) {
		int n = datesForIssue.size();
		int m = datesForRent.size();

		int i = 0;
		int j = 0;

		while (i < n && j < m) {
			if (datesForIssue.get(i).equals(datesForRent.get(j))) {
				i++;
				j++;

				if (j == m) {
					System.out.println("Vratio true");
					return true;
				}

			} else {
				i = i - j + 1;
				j = 0;
			}
		}
		return false;
	}

	/* Methods used for search */
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
		if (apartment.getPrice() >= searchDTO.getMinPrice() && apartment.getPrice() <= searchDTO.getMaxPrice()) {
			return true;
		}
		return false;
	}

	private boolean checkRooms(Apartment apartment, SearchDTO searchDTO) {
		if (apartment.getNumberOfRooms() == searchDTO.getNumberOfRooms()) {
			return true;
		}
		return false;
	}

	private boolean checkGuests(Apartment apartment, SearchDTO searchDTO) {
		if (apartment.getNumberOfGuests() == searchDTO.getNumberOfGuests()) {
			return true;
		}
		return false;
	}

}