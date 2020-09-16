package dto;

public class AmenityDTO {
	private String name;
	private String id;
	public AmenityDTO(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	public AmenityDTO() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
	
}
