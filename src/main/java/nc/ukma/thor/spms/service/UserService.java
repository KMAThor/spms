package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.User;

public interface UserService {
 
    User getUser(String login);
    User getUserById(long id);
}