package com.adammendak.todo.controller;

import com.adammendak.todo.model.User;
import com.adammendak.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        log.info("getting all users");
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findOneById(id);
        if(user.isPresent()) {
            log.info("getting user with id {}", id);
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            log.info("no such user with id {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findOneById(id);
        if(user.isPresent()) {
            userRepository.delete(id);
            log.info("deleting user with id {}",id);
            return new ResponseEntity(HttpStatus.valueOf(204));
        } else {
            log.info("no such user with id {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
