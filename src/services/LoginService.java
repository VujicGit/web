package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Guest;
import beans.Role;
import beans.User;
import dao.impl.GuestDAO;
import dao.impl.UserDAO;
import dto.LoginDTO;

@Path("/login")
public class LoginService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
		if(ctx.getAttribute("guestDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("guestDAO", new GuestDAO(contextPath));
		}
		
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response login(LoginDTO loginDTO) {
		
		if(!userExists(loginDTO.getUsername())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid username or password").build();
			
		}
		
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		User user = userDAO.findById(loginDTO.getUsername());
		System.out.println(user.getUsername());
		if(!isPasswordMatch(user.getPassword(), loginDTO.getPassword())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid username or password").build();
		}
		
		if(user.getRole() == Role.GUEST) {
			GuestDAO guestDAO = (GuestDAO) ctx.getAttribute("guestDAO");
			Guest guest = guestDAO.findById(loginDTO.getUsername());
			System.out.println(guest.getUsername());
			request.getSession(true).setAttribute("loggedInUser", guest);
			
			return Response.ok().entity("indexproba.html").build(); //treba proslediti pocetnu stranicu odgovarajuceg korisnika
		}
		else if(user.getRole() == Role.ADMIN) {
			
			request.getSession().setAttribute("loggedInUser", user);
			return Response.ok().entity("indexproba.html").build();
		}
		else {
			/*HOST*/
		}
		return null;
	}
	
	@GET
	@Path("/reservation/guest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reservationGuest() {
		User user = (User) request.getSession(true).getAttribute("loggedInUser");
		if(user == null || user.getRole() != Role.GUEST) {
			String message = "{\"errorMessage\": \"Please log in as guest to complete acton\"}";
			return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/logout")
	@Produces(MediaType.TEXT_HTML)
	public Response logout() {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("loggedInUser");
		if(session != null && session.getAttribute("loggedInUser") != null) {
			session.invalidate();
		}
		return Response.ok().entity("index.html").build();
	}
	
	@GET
	@Path("/loggedIn/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isUserLoggedIn() {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if(session != null && session.getAttribute("loggedInUser") != null) {
			return Response.ok().build();
		}
		String message = "{\"errorMessage\": \"No user logged in\"}";
		return Response.status(Response.Status.FORBIDDEN).entity(message).build();
	}
	
	private boolean userExists(String username) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.existsById(username);
	}
	
	private boolean isPasswordMatch(String userPassword, String loginDTOPassword) {
		return userPassword.toLowerCase().equals(loginDTOPassword.toLowerCase());
	}
	
	@GET
	@Path("/loggedIn/admin")
	public Response isAdminLoggedIn() {
		System.out.println("Usao");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if(session != null && session.getAttribute("loggedInUser") != null && user.getRole() == Role.ADMIN) {
			return Response.ok().build();
		}
		
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	
	
}
