package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;

public interface RoleRepository {
	
	Role getRoleById(long id);
	Role getRoleByName(String name);
	Role getRoleByUser(User user);
}
