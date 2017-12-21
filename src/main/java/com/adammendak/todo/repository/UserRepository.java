package com.adammendak.todo.repository;

import com.adammendak.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findUserByEmail (String email);


}
