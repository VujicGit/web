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
import beans.User;

public class AdminDAO implements dao.cruddao.AdminDAO {

	private HashMap<String, Admin> admins;
	private String contextPath;
	
	public AdminDAO() {
		
	}
	
	public AdminDAO(String contextPath) {
		this.contextPath = contextPath;
		loadAdmins(contextPath);
	}
	
	@Override
	public int count() {
		 ArrayList<Admin> adminList = new ArrayList<Admin>(findAll());
		 return adminList.size();
	}

	@Override
	public boolean add(Admin entity) {
		if(existsById(entity.getUsername())) {
			return false;
		}
		admins.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean update(Admin entity) {
		if(!existsById(entity.getUsername())) {
			return false;
		}
		admins.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Admin entity) {
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
		return admins.containsKey(id);
	}

	@Override
	public boolean isDeleted(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Admin> findAll() {
		loadAdmins(contextPath);;
		Collection<Admin> adminList = admins.values();
		return adminList;
	}

	@Override
	public Admin findById(String id) {
		return admins.get(id);
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(contextPath + File.separator + "data" + File.separator + "users.json");
		
		try {
			mapper.writeValue(file, admins);
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
	public boolean saveAll(Collection<Admin> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadAdmins(String contextPath) {
		BufferedReader in = null;
		File file = new File(contextPath + File.separator + "data" + File.separator + "admins.json");
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Admin>> typeRef 
        = new TypeReference<HashMap<String,Admin>>() {};
        
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			admins = mapper.readValue(in, typeRef);
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
