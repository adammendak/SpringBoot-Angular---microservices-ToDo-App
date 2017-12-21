package com.adammendak.todo.controller;

import com.adammendak.todo.model.User;
import com.adammendak.todo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findOneById(id);
        if(user.isPresent()) {
            userRepository.delete(id);
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findOneById(id);
        if(user.isPresent()) {
            userRepository.delete(id);
            return new ResponseEntity(HttpStatus.valueOf(204));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
