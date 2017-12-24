package com.adammendak.todo.controller;

import com.adammendak.todo.model.ToDo;
import com.adammendak.todo.repository.ToDoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class ToDoController {

    private ToDoRepository toDoRepository;

    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/toDo")
    public ResponseEntity<List<ToDo>> getAll() {
        log.info("getting all ToDo's");
        return new ResponseEntity<List<ToDo>>(toDoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/toDo/{id}")
    public ResponseEntity<ToDo> getToDo(@PathVariable Long id) {
        Optional<ToDo> optionalToDo = toDoRepository.findOneById(id);
        if(optionalToDo.isPresent()) {
            log.info("getting ToDo with id {}", id);
            return new ResponseEntity<ToDo>(optionalToDo.get(), HttpStatus.OK);
        } else {
            log.info("no such toDo");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/toDo/user/{id}")
    public ResponseEntity<List<ToDo>> getAllToDosByUserId(@PathVariable Long id) {
        List<ToDo> toDoList = toDoRepository.findAllByFkUser(id);
        if(toDoList.size() != 0){
            log.info("returning {} ToDo's of User with Id {}", toDoList.size(), id);
            return new ResponseEntity<List<ToDo>>(toDoList, HttpStatus.OK);
        } else {
            log.info("none found for id {}", id);
            return new ResponseEntity<List<ToDo>>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/toDo/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable Long id) {
        Optional<ToDo> optionalToDo = toDoRepository.findOneById(id);
        if(optionalToDo.isPresent()) {
            toDoRepository.delete(id);
            log.info("deleted ToDo with id {}", id);
            return new ResponseEntity(HttpStatus.valueOf(204));
        } else {
            log.info("not found any toDo with id {}", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
