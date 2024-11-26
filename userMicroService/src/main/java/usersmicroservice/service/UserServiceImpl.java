package usersmicroservice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usersmicroservice.Exception.EmailAlreadyExistsException;
import usersmicroservice.Exception.ExpiredTokenException;
import usersmicroservice.Exception.InvalidTokenException;
import usersmicroservice.entities.Role;
import usersmicroservice.entities.User;
import usersmicroservice.entities.VerificationToken;
import usersmicroservice.register.RegistationRequest;
import usersmicroservice.repos.RoleRepository;
import usersmicroservice.repos.UserRepository;
import usersmicroservice.repos.VerificationTokenRepo;
import usersmicroservice.util.EmailSender;


@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRep;
    @Autowired
    RoleRepository roleRep;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
    VerificationTokenRepo verificationTokenRepo;
	@Autowired
    EmailSender emailSender;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        if (usr == null) {
            throw new RuntimeException("User not found: " + username);
        }
        
        Role r = roleRep.findByRole(rolename);
        if (r == null) {
            throw new RuntimeException("Role not found: " + rolename);
        }
        
        // Add role to user's roles list
        usr.getRoles().add(r);
        
        // Save the user to persist the relationship in the join table
        return userRep.save(usr);
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRep.findAll();
    }

    // Implement getUserByUsername
    @Override
    public User getUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    // Implement getRoleByName
    @Override
    public Role getRoleByName(String roleName) {
        return roleRep.findByRole(roleName);
    }

	@Override
    public User registerUser(RegistationRequest request) {
        Optional<User> optionalUser = userRep.findByEmail(request.getEmail());
        if (optionalUser.isPresent())
            throw new EmailAlreadyExistsException("Email déjà existant!");

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setEnabled(false);

        // Assign the default role "USER" to newUser
        Role r = roleRep.findByRole("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);

        userRep.save(newUser);

        // Generate and save the verification token
        String code = generateCode();
        VerificationToken token = new VerificationToken(code, newUser);
        verificationTokenRepo.save(token);
		//envoyer par email pour valider l'email de l'utilisateur
		sendEmailUser(newUser,token.getToken());

        return newUser;
    }

    public String generateCode() {
        Random random = new Random();
        Integer code = 100000 + random.nextInt(900000);
        return code.toString();
    }

	public void sendEmailUser(User u, String code) {
		String emailBody ="Bonjour "+ "<h1>"+u.getUsername() +"</h1>" +
		" Votre code de validation est "+"<h1>"+code+"</h1>";
	   emailSender.sendEmail(u.getEmail(), emailBody);
	}

	@Override
	public User validateToken(String code) {
		VerificationToken token = verificationTokenRepo.findByToken(code);
		if (token == null) {
			throw new InvalidTokenException("Invalid Token");
		}
	
		User user = token.getUser();
		Calendar calendar = Calendar.getInstance();
		if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
			verificationTokenRepo.delete(token);
			throw new ExpiredTokenException("Expired Token");
		}
		user.setEnabled(true);
		userRep.save(user);
		return user;
	}
	

}
