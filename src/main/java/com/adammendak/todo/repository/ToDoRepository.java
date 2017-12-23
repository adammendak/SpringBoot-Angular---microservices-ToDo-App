package com.adammendak.todo.repository;

import com.adammendak.todo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long>{

    List<ToDo> findByFkUser (String fkUser);

    Optional<ToDo> findOneById (Long id);

}
