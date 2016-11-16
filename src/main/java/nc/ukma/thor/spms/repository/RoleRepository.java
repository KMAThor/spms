package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;

public interface RoleRepository {
	
	public Role getRoleById(Long id);
	public Role getRoleByName(String name);
	public Role getRoleByUser(Long userId);
	public List<Role> getAllRoles();
	
	public default Role getRoleByUser(User user){
		return getRoleByUser(user.getId());
	}
}
