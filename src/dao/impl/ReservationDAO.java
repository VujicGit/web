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

import beans.Reservation;

public class ReservationDAO implements dao.cruddao.ReservationDAO {

	private HashMap<String, Reservation> reservations;
	private String contextPath;

	public ReservationDAO() {

	}

	public ReservationDAO(String contextPath) {
		this.contextPath = contextPath;
		loadReservations(contextPath);
	}
	
	@Override
	public int count() {
		ArrayList<Reservation> apartmentsList = new ArrayList<Reservation>(findAll());
		return apartmentsList.size();

	}

	@Override
	public boolean add(Reservation entity) {
		if (existsById(entity.getId())) {
			return false;
		}
		reservations.put(entity.getId(), entity);
		save();
		return true;
	}

	@Override
	public boolean update(Reservation entity) {
		if (!existsById(entity.getId()) || isDeleted(entity.getId())) {
			return false;
		}
		reservations.put(entity.getId(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Reservation entity) {
		if (!existsById(entity.getId()) || isDeleted(entity.getId())) {
			return false;
		}
		Reservation reservation = findById(entity.getId());
		reservation.setDeleted(true);
		reservations.put(reservation.getId(), reservation);
		save();
		return true;
	}

	@Override
	public void deleteAll() {
		reservations.clear();

	}

	@Override
	public boolean deleteById(String id) {
		if (!existsById(id)) {
			return false;
		}
		Reservation reservation = findById(id);
		reservation.setDeleted(true);
		save();
		return true;
	}

	@Override
	public boolean existsById(String id) {
		return reservations.containsKey(id);
	}

	@Override
	public boolean isDeleted(String id) {
		Reservation reservation = findById(id);
		return reservation.isDeleted();
	}

	@Override
	public Collection<Reservation> findAll() {
		loadReservations(contextPath);
		Collection<Reservation> reservationList = reservations.values();
		for (Reservation apartment : reservationList) {
			if (isDeleted(apartment.getId())) {
				reservationList.remove(apartment);
			}
		}
		return reservationList;
	}

	@Override
	public Reservation findById(String id) {
		return reservations.get(id);
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(contextPath + File.separator + "data" + File.separator + "reservations.json");

		try {
			mapper.writeValue(file, reservations);
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
	public boolean saveAll(Collection<Reservation> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadReservations(String contextPath) {
		BufferedReader in = null;
		File file = new File(contextPath + File.separator + "data" + File.separator + "reservations.json");

		System.out.println(file.getPath());

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, Reservation>> typeRef = new TypeReference<HashMap<String, Reservation>>() {
		};

		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			reservations = mapper.readValue(in, typeRef);
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
