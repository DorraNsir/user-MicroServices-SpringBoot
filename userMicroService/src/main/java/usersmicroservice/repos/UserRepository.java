package usersmicroservice.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import usersmicroservice.entities.User;

public interface UserRepository  extends JpaRepository<User, Long> {
	User findByUsername(String username);
	Optional<User> findByEmail(String email);
}
