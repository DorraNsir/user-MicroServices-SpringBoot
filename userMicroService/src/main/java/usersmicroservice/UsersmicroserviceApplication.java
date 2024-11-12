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
	// //ajouter les rôles
	// userService.addRole(new Role(null,"ADMIN"));
	// userService.addRole(new Role(null,"USER"));
	// //ajouter les users
	// userService.saveUser(new User(null,"admin","123",true,null));
	// userService.saveUser(new User(null,"nadhem","123",true,null));
	// userService.saveUser(new User(null,"yassine","123",true,null));
	// //ajouter les rôles aux users
	// userService.addRoleToUser("admin", "ADMIN");
	// userService.addRoleToUser("admin", "USER");
	// userService.addRoleToUser("nadhem", "USER");
	// userService.addRoleToUser("yassine", "USER");
	// } 

}
