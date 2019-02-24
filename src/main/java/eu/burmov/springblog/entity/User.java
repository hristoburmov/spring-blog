package eu.burmov.springblog.entity;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
	
	@Id
	@NotNull(message = "{username.null}")
	@Size(max = 32, min = 2, message = "{username.size}")
	private String username;
	
	@NotNull(message = "{password.null}")
	@Size(max = 64, min = 8, message = "{password.size}")
	private String password;

	// Constructors
	public User() {}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Getters and setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
