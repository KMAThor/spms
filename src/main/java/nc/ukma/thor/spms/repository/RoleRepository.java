package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;

public interface RoleRepository {
	
	Role getRoleById(long id);
	Role getRoleByName(String name);
	Role getRoleByUser(User user);
	List<Role> getAllRoles();
}
