package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Reservation;
import dao.impl.ReservationDAO;

@Path("/reservations")
public class ReservationService {

	@Context
	ServletContext ctx;
	
	public ReservationService() {
		System.out.println("dadada");
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("reservationDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("reservationDAO", new ReservationDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> getAllReservations() {
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.findAll();
	}
}
