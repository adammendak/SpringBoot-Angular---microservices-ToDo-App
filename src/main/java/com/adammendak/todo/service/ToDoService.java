package com.adammendak.todo.service;

import com.adammendak.todo.model.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoService {


    List<ToDo> getToDos (String email);

    ToDo addToDo (ToDo toDo);

    void deleteToDo (Long id);

    Optional<ToDo> findOneById (Long id);

    List<ToDo> findAllToDosByfkUser (String fkUser);

}
