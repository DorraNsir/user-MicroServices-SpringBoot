package usersmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import usersmicroservice.entities.Role;
import usersmicroservice.entities.User;
import usersmicroservice.service.UserService;

@SpringBootApplication
public class UsersmicroserviceApplication {

		@Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(UsersmicroserviceApplication.class, args);
	}
	
	// @PostConstruct
	// void init_users() {
	// 	// Check if roles already exist before adding
	// 	if (userService.getRoleByName("ADMIN") == null) {
	// 		userService.addRole(new Role(null, "ADMIN"));
	// 	}
	// 	if (userService.getRoleByName("USER") == null) {
	// 		userService.addRole(new Role(null, "USER"));
	// 	}
	
	// 	// Check if users already exist before adding
	// 	if (userService.getUserByUsername("admin") == null) {
	// 		userService.saveUser(new User(null, "admin", "123", true, null));
	// 	}
	// 	if (userService.getUserByUsername("nadhem") == null) {
	// 		userService.saveUser(new User(null, "nadhem", "123", true, null));
	// 	}
	// 	if (userService.getUserByUsername("yassine") == null) {
	// 		userService.saveUser(new User(null, "dorra", "123", true, null));
	// 	}
	
	// 	// Assign roles to users if not already assigned
	// 	userService.addRoleToUser("admin", "ADMIN");
	// 	userService.addRoleToUser("admin", "USER");
	// 	userService.addRoleToUser("nadhem", "USER");
	// 	userService.addRoleToUser("yassine", "USER");
	// }
	

}
