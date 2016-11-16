package nc.ukma.thor.spms.entity;

public class User{
	
	private Long id;
	private String email;
	private String firstName;
	private String secondName;
	private String lastName;
	private String password;
	private boolean isActive;
	private String linkToPhoto;
	private Role role;
	
	public User() {}

	public User(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getLinkToPhoto() {
		return linkToPhoto;
	}

	public void setLinkToPhoto(String linkToPhoto) {
		this.linkToPhoto = linkToPhoto;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", secondName=" + secondName
				+ ", lastName=" + lastName + ", password=" + password + ", isActive=" + isActive + ", linkToPhoto="
				+ linkToPhoto + ", role=" + role + "]";
	}


	
}
