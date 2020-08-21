package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Apartment;

public class ApartmentDAO implements dao.cruddao.ApartmentDAO {

	private HashMap<String, Apartment> apartments;
	private String contextPath;
	
	public ApartmentDAO() {
		
	}
	
	public ApartmentDAO(String contextPath) {
		this.contextPath = contextPath;
		loadApartments(contextPath);
	}
	@Override
	public int count() {
		ArrayList<Apartment> apartmentsList = new ArrayList<Apartment>(findAll());
		return apartmentsList.size();
		
	}

	
	
	@Override
	public void delete(Apartment entity) {
		apartments.remove(entity.getId());

	}

	@Override
	public void deleteAll() {
		apartments.clear();

	}

	@Override
	public void deleteById(String id) {
		apartments.remove(id);

	}

	@Override
	public boolean existsById(String id) {
		return apartments.containsKey(id);
	}

	@Override
	public Collection<Apartment> findAll() {
		// TODO Auto-generated method stub
		return apartments.values();
	}

	@Override
	public Apartment findById(String id) {
		return apartments.get(id);
	}

	@Override
	public boolean save(Apartment entity) {
		
		if(apartments.containsKey(entity.getId())) {
			return false;
		}
		apartments.put(entity.getId(), entity);
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(contextPath + File.separator + "data" + File.separator + "apartments.json");
		
		try {
			mapper.writeValue(file, apartments);
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
	public boolean saveAll(Collection<Apartment> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadApartments(String contextPath) {
		BufferedReader in = null;
		File file = new File(contextPath + File.separator + "data" + File.separator + "apartments.json");
		System.out.println(file.getPath());
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Apartment>> typeRef 
        = new TypeReference<HashMap<String,Apartment>>() {};
        
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			apartments = mapper.readValue(in, typeRef);
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