package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Admin;
import beans.Guest;

public class GuestDAO implements dao.cruddao.GuestDAO {

	
	private HashMap<String, Guest> guests;
	private String contextPath;
	
	public GuestDAO() {
		
	}
	
	public GuestDAO(String contextPath) {
		this.contextPath = contextPath;
		loadGuests(contextPath);
	}
	@Override
	public int count() {
		ArrayList<Guest> guesList = new ArrayList<Guest>(findAll());
		 return guesList.size();
	}

	@Override
	public boolean add(Guest entity) {
		if(existsById(entity.getUsername())) {
			return false;
		}
		guests.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean update(Guest entity) {
		if(!existsById(entity.getUsername())) {
			return false;
		}
		guests.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Guest entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsById(String id) {
		return guests.containsKey(id);
	}

	@Override
	public boolean isDeleted(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Guest> findAll() {
		loadGuests(contextPath);;
		Collection<Guest> guestList = guests.values();
		return guestList;
	}

	@Override
	public Guest findById(String id) {
		return guests.get(id);
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(contextPath + File.separator + "data" + File.separator + "guests.json");
		
		try {
			mapper.writeValue(file, guests);
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
		return true;
	}

	@Override
	public boolean saveAll(Collection<Guest> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadGuests(String contextPath) {
		BufferedReader in = null;
		File file = new File(contextPath + File.separator + "data" + File.separator + "guests.json");
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Guest>> typeRef 
        = new TypeReference<HashMap<String,Guest>>() {};
        
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			guests = mapper.readValue(in, typeRef);
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
		
	
	}

}
