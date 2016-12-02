package nc.ukma.thor.spms.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class SpmsUserDetails extends org.springframework.security.core.userdetails.User{

	private long id;
	private static final long serialVersionUID = 8260610903193252152L;
	
	public SpmsUserDetails(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
