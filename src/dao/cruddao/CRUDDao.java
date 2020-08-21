package dao.cruddao;

import java.util.Collection;

public interface CRUDDao<T, ID> {

	int count();
	
	void delete(T entity);
	
	void deleteAll();
	
	void deleteById(ID id);
	
	boolean existsById(ID id);
	
	Collection<T> findAll();
	
	T findById(ID id);
	
	boolean save(T entity);
	
	boolean saveAll(Collection<T> entities);
}
