package com.adammendak.todo.Bootstrap;

import com.adammendak.todo.model.ToDo;
import com.adammendak.todo.model.User;
import com.adammendak.todo.repository.ToDoRepository;
import com.adammendak.todo.repository.UserRepository;
import com.adammendak.todo.utility.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner{

    private UserRepository userRepository;
    private ToDoRepository toDoRepository;
    private EncryptionUtil encryptionUtil;

    public Bootstrap(UserRepository userRepository, ToDoRepository toDoRepository, EncryptionUtil encryptionUtil) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
        this.encryptionUtil = encryptionUtil;
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("injecting into h2 database");

        String encryptedPassword = encryptionUtil.encrypt("myPassword");
        User firstUser = new User("myName", "myEmail@gmail.com", encryptedPassword);
        userRepository.save(firstUser);

        String secondEncrypted = encryptionUtil.encrypt("secretPassword");
        User secondUser = new User("otherName", "other@email.com", secondEncrypted);
        userRepository.save(secondUser);

        ToDo firstToDo = new ToDo();
        firstToDo.setDescription("codeHard");
        firstToDo.setPriority("high");
        firstToDo.setFkUser(1L);
        toDoRepository.save(firstToDo);

        ToDo secondOne = new ToDo();
        secondOne.setDescription("lol");
        secondOne.setPriority("low");
        secondOne.setFkUser(2L);
        toDoRepository.save(secondOne);

    }

}
