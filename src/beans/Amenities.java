package beans;

public class Amenities {
	
	private String id;
	private String name;
	private boolean deleted;
	public Amenities() {
		super();
	}
	public Amenities(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.deleted = false;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
