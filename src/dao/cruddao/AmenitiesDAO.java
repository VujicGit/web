package dao.cruddao;

import beans.Amenities;

public interface AmenitiesDAO extends CRUDDao<Amenities, String> {

	public boolean existsByName(String name);
	public String generateId();
	public void saveGeneratedId(String id);
}
