package usersmicroservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import usersmicroservice.entities.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}