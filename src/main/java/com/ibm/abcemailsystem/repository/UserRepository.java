package com.ibm.abcemailsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.abcemailsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	Optional<User> findById(Long id);
}
