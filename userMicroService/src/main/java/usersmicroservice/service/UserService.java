package usersmicroservice.service;

import java.util.List;

import usersmicroservice.entities.Role;
import usersmicroservice.entities.User;
import usersmicroservice.register.RegistationRequest;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    List<User> findAllUsers();

    // Add these methods to retrieve by username and role name
    User getUserByUsername(String username);
    Role getRoleByName(String roleName);
	public User registerUser(RegistationRequest request);
	public User validateToken(String code);
}
