package nc.ukma.thor.spms.entity;

public class ApplicationForm {
	
	private User user;
	private String photoScope;
	
	public ApplicationForm() {}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPhotoScope() {
		return photoScope;
	}

	public void setPhotoScope(String photoScope) {
		this.photoScope = photoScope;
	}
}
