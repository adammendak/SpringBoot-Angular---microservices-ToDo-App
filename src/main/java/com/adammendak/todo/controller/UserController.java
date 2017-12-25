package com.adammendak.todo.controller;

import com.adammendak.todo.model.User;
import com.adammendak.todo.repository.UserRepository;
import com.adammendak.todo.utility.EncryptionUtil;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    private EncryptionUtil encryptionUtil;

    public UserController(UserRepository userRepository, EncryptionUtil encryptionUtil) {
        this.userRepository = userRepository;
        this.encryptionUtil = encryptionUtil;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
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

    @PostMapping("/user")
    public ResponseEntity<User> newUser(@RequestBody @Valid User formUser ) {
        User newUser = new User();
        newUser.setUserName(formUser.getUserName());
        newUser.setPassword(encryptionUtil.encrypt(formUser.getPassword()));
        newUser.setEmail(formUser.getEmail());
        try {
            userRepository.save(newUser);
            log.info("new user created with id {}", newUser.getId());
        } catch (Exception e) {
            log.info("error {}", e.getMessage());
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
