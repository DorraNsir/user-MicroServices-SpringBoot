package usersmicroservice.service;

import java.util.List;

import usersmicroservice.entities.Role;
import usersmicroservice.entities.User;

public interface UserService {
	User saveUser(User user);
	User findUserByUsername (String username);
	Role addRole(Role role);
	User addRoleToUser(String username, String rolename);
	List<User> findAllUsers();
}
