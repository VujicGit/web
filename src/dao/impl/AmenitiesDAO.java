package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenities;
import beans.Apartment;

public class AmenitiesDAO implements dao.cruddao.AmenitiesDAO {

	private HashMap<String, Amenities> amenities;
	private String contextPath;

	public AmenitiesDAO() {

	}

	public AmenitiesDAO(String contextPath) {
		this.contextPath = contextPath;
		loadAmenities(contextPath);
	}

	@Override
	public int count() {
		loadAmenities(contextPath);
		ArrayList<Amenities> amenitiesList = new ArrayList<Amenities>(findAll());
		return amenities.size();
	}

	@Override
	public boolean add(Amenities entity) {
		loadAmenities(contextPath);
		if (existsById(entity.getId())) {
			return false;
		}
		amenities.put(entity.getId(), entity);
		save();
		return true;
	}

	@Override
	public boolean update(Amenities entity) {
		loadAmenities(contextPath);
		if (!existsById(entity.getId()) || isDeleted(entity.getId())) {
			return false;
		}
		amenities.put(entity.getId(), entity);

		save();
		return true;
	}

	@Override
	public boolean delete(Amenities entity) {
		loadAmenities(contextPath);
		if (!existsById(entity.getId()) || isDeleted(entity.getId())) {
			return false;
		}
		Amenities amenity = findById(entity.getId());
		amenity.setDeleted(true);
		amenities.put(amenity.getId(), amenity);
		save();
		return true;
	}

	@Override
	public void deleteAll() {
		loadAmenities(contextPath);
		amenities.clear();
	}

	@Override
	public boolean deleteById(String id) {
		loadAmenities(contextPath);
		if (!existsById(id)) {
			return false;
		}
		Amenities amenities = findById(id);
		amenities.setDeleted(true);
		save();
		return true;
	}

	@Override
	public boolean existsById(String id) {
		loadAmenities(contextPath);
		if (!amenities.containsKey(id)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDeleted(String id) {
		loadAmenities(contextPath);
		Amenities amentity = findById(id);
		return amentity.isDeleted();
	}

	@Override
	public Collection<Amenities> findAll() {
		loadAmenities(contextPath);
		return amenities.values();
	}

	@Override
	public Amenities findById(String id) {
		loadAmenities(contextPath);
		return amenities.get(id);
	}

	@Override
	public boolean existsByName(String name) {
		boolean exists = false;
		loadAmenities(contextPath);
		ArrayList<Amenities> amenitiesList = new ArrayList<Amenities>(findAll());
		for (Amenities amenities : amenitiesList) {
			if (amenities.getName().toLowerCase().equals(name.toLowerCase())) {
				exists = true;
				return exists;
			}
		}
		return exists;
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(contextPath + File.separator + "data" + File.separator + "amenities.json");

		try {
			mapper.writeValue(file, amenities);
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
	public boolean saveAll(Collection<Amenities> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	public void loadAmenities(String contextPath) {

		BufferedReader in = null;
		File file = new File(contextPath + File.separator + "data" + File.separator + "amenities.json");

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, Amenities>> typeRef = new TypeReference<HashMap<String, Amenities>>() {
		};

		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			amenities = mapper.readValue(in, typeRef);
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

	@Override
	public String generateId() {
		File file = new File(contextPath + File.separator + "data" + File.separator + "amenitiesId.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
		int idInt = Integer.parseInt(line);
		idInt++;

		return String.valueOf(idInt);
	}

	@Override
	public void saveGeneratedId(String id) {
		File file = new File(contextPath + File.separator + "data" + File.separator + "amenitiesId.txt");
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id);
		writer.print(id);
		writer.close();

	}

}
