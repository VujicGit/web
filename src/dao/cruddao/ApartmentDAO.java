package dao.cruddao;

import java.util.Collection;

import beans.Apartment;

public interface ApartmentDAO extends CRUDDao<Apartment, String> {

	public Collection<Apartment> getAllActiveApartments();
}
