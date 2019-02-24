package eu.burmov.springblog.entity;

public enum Role {
	
	USER("User"),
	ADMIN("Admin");
	
	private final String role;
	
	// Constructors
	private Role(String role) {
		this.role = role;
	}
	
	// Getters
	public String getRole() {
		return role;
	}
	
}
