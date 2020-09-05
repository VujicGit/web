package services;

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

import beans.Guest;
import beans.Role;
import beans.User;
import dao.impl.GuestDAO;
import dao.impl.UserDAO;

@Path("/register")
public class RegisterService {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public RegisterService() {
		
	}
	
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
	public Response registerUser(User user) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		if(existsByUsername(user.getUsername())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Korisnicko ime mora biti jedinstveno").build();
		}
		if(user.getRole() == Role.GUEST) {
			Guest guest = new Guest(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getRole());
			GuestDAO guestDAO = (GuestDAO) ctx.getAttribute("guestDAO");
			guestDAO.add(guest);
			userDAO.add(user);
			request.getSession().setAttribute("loggedInUser", guest);
			return null;
		
			
		}
	
		return null;
	}
	
	@GET
	@Path("/testlogin")
	@Produces(MediaType.TEXT_PLAIN)
	public String testLogin() {
		User user = null;
		user = (User)request.getSession().getAttribute("loggedInUser");
		if(user == null) {
			return "No user logged in";
		}
		return user.getName() + " " + user.getSurname();
	}
	private boolean existsByUsername(String username) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		return userDAO.existsById(username);
	}
	
	
	
}
