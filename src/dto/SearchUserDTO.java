package dto;

import beans.Gender;
import beans.Role;

public class SearchUserDTO {
	private String text;

	public SearchUserDTO() {
		super();
	}

	public SearchUserDTO(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
