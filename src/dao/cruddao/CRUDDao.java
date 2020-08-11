package dao.cruddao;

public interface CRUDDao<T, ID> {

	int count();
	
	void delete(T entity);
	
	void deleteAll();
	
	void deleteById(ID id);
	
	boolean existsById(ID id);
	
	Iterable<T> findAll();
	
	T findById(ID id);
	
	void save(T entity);
	
	void saveAll(Iterable<T> entities);
}
