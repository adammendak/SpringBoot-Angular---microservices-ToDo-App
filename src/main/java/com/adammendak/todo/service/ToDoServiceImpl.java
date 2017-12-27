package com.adammendak.todo.service;

import com.adammendak.todo.model.ToDo;
import com.adammendak.todo.repository.ToDoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ToDoServiceImpl implements ToDoService{

    private ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public List<ToDo> getToDos(String email) {
        return toDoRepository.findAllByFkUser(email);
    }

    @Override
    public ToDo addToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    @Override
    public void deleteToDo(Long id) {
        toDoRepository.delete(id);
    }

    @Override
    public Optional<ToDo> findOneById(Long id) {
        return toDoRepository.findOneById(id);
    }
}
