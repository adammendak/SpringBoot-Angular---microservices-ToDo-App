package com.adammendak.todo.controller;

import com.adammendak.todo.exception.UserNotLoggedException;
import com.adammendak.todo.model.ToDo;
import com.adammendak.todo.service.LoginService;
import com.adammendak.todo.service.ToDoService;
import com.adammendak.todo.utility.ToDoValidator;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ToDoController {

    private ToDoService toDoService;
    private LoginService loginService;

    public ToDoController(ToDoService toDoService, LoginService loginService) {
        this.toDoService = toDoService;
        this.loginService = loginService;
    }

    @GetMapping("/toDo")
    public ResponseEntity<List<ToDo>> getAll(HttpServletRequest request) {
        log.info("getting all ToDo's");
        try {
            Map<String, Object> userData =loginService.verifyJwtAndGetData(request);
            return new ResponseEntity<List<ToDo>>(toDoService.getToDos((String)userData.get("email")), HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            log.info("Exception {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (UserNotLoggedException e2) {
            log.info("Exception {}", e2.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        } catch (ExpiredJwtException e3) {
            log.info("Exception{}", e3.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(Collections.emptyList());
        }
    }

    @PostMapping("/toDo")
    public ResponseEntity<ToDo> addToDo(HttpServletRequest request, @Valid ToDo toDo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(toDo, result);

        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(toDo);
        }

        try {
            loginService.verifyJwtAndGetData(request);
            toDoService.addToDo(toDo);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDo);

        } catch (UnsupportedEncodingException e) {
            log.info("Exception {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(toDo);
        } catch (UserNotLoggedException e2) {
            log.info("Exception {}", e2.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(toDo);
        } catch (ExpiredJwtException e3) {
            log.info("Exception{}", e3.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(toDo);
        }
    }

    @GetMapping("/toDo/{id}")
    public ResponseEntity<ToDo> getToDo(HttpServletRequest request, @PathVariable Long id) {
        try {
            loginService.verifyJwtAndGetData(request);
            Optional<ToDo> optionalToDo = toDoService.findOneById(id);
            if(optionalToDo.isPresent()) {
                log.info("ToDo to return {}", optionalToDo.get().toString());
                return ResponseEntity.status(HttpStatus.OK).body(optionalToDo.get());
            } else {
                log.info("no such toDo");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (UnsupportedEncodingException e) {
            log.info("Exception {}", e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (UserNotLoggedException e2) {
            log.info("Exception {}", e2.getMessage());
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } catch (ExpiredJwtException e3) {
            log.info("Exception{}", e3.getMessage());
            return new ResponseEntity(HttpStatus.GATEWAY_TIMEOUT);
        }
    }

//    @GetMapping("/toDo/user/{id}")
//    public ResponseEntity<List<ToDo>> getAllToDosByUserId(@PathVariable Long id) {
//        List<ToDo> toDoList = toDoRepository.findAllByFkUser(id);
//        if(toDoList.size() != 0){
//            log.info("returning {} ToDo's of User with Id {}", toDoList.size(), id);
//            return new ResponseEntity<List<ToDo>>(toDoList, HttpStatus.OK);
//        } else {
//            log.info("none found for id {}", id);
//            return new ResponseEntity<List<ToDo>>(HttpStatus.NOT_FOUND);
//        }
//
//    }

    @DeleteMapping("/toDo/{id}")
    public ResponseEntity deleteToDo(HttpServletRequest request, @PathVariable Long id) {
        try {
            loginService.verifyJwtAndGetData(request);
            toDoService.deleteToDo(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            log.info("Exception {}", e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (UserNotLoggedException e2) {
            log.info("Exception {}", e2.getMessage());
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } catch (ExpiredJwtException e3) {
            log.info("Exception{}", e3.getMessage());
            return new ResponseEntity(HttpStatus.GATEWAY_TIMEOUT);
        }
//        Optional<ToDo> optionalToDo = toDoRepository.findOneById(id);
//        if(optionalToDo.isPresent()) {
//            toDoRepository.delete(id);
//            log.info("deleted ToDo with id {}", id);
//            return new ResponseEntity(HttpStatus.valueOf(204));
//        } else {
//            log.info("not found any toDo with id {}", id);
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
    }


}
