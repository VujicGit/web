package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Apartment;
import beans.Reservation;

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
	public boolean add(Apartment entity) {
		if (existsById(entity.getId())) {
			return false;
		}
		apartments.put(entity.getId(), entity);
		save();
		return true;
	}

	@Override
	public boolean update(Apartment entity) {
		if (!existsById(entity.getId()) || isDeleted(entity.getId())) {
			return false;
		}
		apartments.put(entity.getId(), entity);
		
		save();
		return true;

	}

	@Override
	public boolean delete(Apartment entity) {
		if (!existsById(entity.getId()) || isDeleted(entity.getId())) {
			return false;
		}
		Apartment apartment = findById(entity.getId());
		apartment.setDeleted(true);
		apartments.put(apartment.getId(), apartment);
		save();
		return true;

	}

	@Override
	public void deleteAll() {
		apartments.clear();

	}

	public boolean deleteById(String id) {
		if (!existsById(id)) {
			return false;
		}
		Apartment apartment = findById(id);
		apartment.setDeleted(true);
		save();
		return true;

	}

	@Override
	public boolean existsById(String id) {
		if(isDeleted(id) || !apartments.containsKey(id)) {
			return false;
		}
		return true;
		
	}

	@Override
	public boolean isDeleted(String id) {
		Apartment apartment = findById(id);
		return apartment.isDeleted();
	}

	@Override
	public Collection<Apartment> findAll() {
		// TODO Auto-generated method stub

		loadApartments(contextPath);
		Collection<Apartment> apartmentsList = apartments.values();
		for (Apartment apartment : apartmentsList) {
			if (isDeleted(apartment.getId())) {
				apartmentsList.remove(apartment);
			}
		}
		return apartmentsList;

	}

	@Override
	public Apartment findById(String id) {
		return apartments.get(id);
	}

	@Override

	public boolean save() {

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

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, Apartment>> typeRef = new TypeReference<HashMap<String, Apartment>>() {
		};

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
