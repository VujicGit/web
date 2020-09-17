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

import beans.User;
import dao.impl.AmenitiesDAO;
import dao.impl.ApartmentDAO;
import dao.impl.UserDAO;
import dto.SearchUserDTO;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;

	@Context
	HttpServletRequest request;

	public UserService() {

	}

	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}

	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		return userDAO.findAll();
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<User> search(SearchUserDTO searchUserDTO) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		String text = searchUserDTO.getText();
		ArrayList<User> users = new ArrayList<User>(userDAO.findAll());

		if (text.equals("")) {
			return users;
		}
		
	
		
		System.out.println(text);
		for (User user : new ArrayList<User>(users)) {
			if (!equalsByUsername(user, text) && !equalsByName(user, text) && !equalsBySurname(user, text)
					&& !equalsByRole(user, text) && !equalsByGender(user, text)) {
				users.remove(user);
			}
			/*
			 * if(!equalsByName(user, text)) { users.remove(user); }
			 * if(!equalsBySurname(user, text)) { users.remove(user); }
			 * if(!equalsByRole(user, text)) { users.remove(user); }
			 * if(!equalsByGender(user, text)) { users.remove(user); }
			 */
		}
		return users;
	}

	@POST
	@Path("/changeInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeUserInfo(User user) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		if(userDAO.existsById(user.getUsername())) {
			System.out.println(user.getName());
			User oldUser = userDAO.findById(user.getUsername());
			user.setRole(oldUser.getRole());
			userDAO.update(user);
			return Response.ok().build();
		}
		
		
		String message = "{\"errorMessage\": \"Can not change user info\"}";
		return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
		
	}
	
	private boolean equalsByUsername(User user, String text) {
		return user.getUsername().toLowerCase().equals(text.toLowerCase());
	}

	private boolean equalsByName(User user, String text) {
		return user.getName().toLowerCase().equals(text.toLowerCase());
	}

	private boolean equalsBySurname(User user, String text) {
		return user.getSurname().toLowerCase().equals(text.toLowerCase());
	}

	private boolean equalsByRole(User user, String text) {
		return user.getRole().toString().toLowerCase().equals(text.toLowerCase());
	}

	private boolean equalsByGender(User user, String text) {
		return user.getGender().toString().toLowerCase().equals(text.toLowerCase());
	}

}
