package com.adammendak.todo.utility;

import com.adammendak.todo.model.ToDo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ToDoValidator implements Validator {

    @Override
    public boolean supports(Class<?> Clazz) {
        return ToDo.class.equals(Clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ToDo toDo = (ToDo) o;

        String priority = toDo.getPriority();
        if(!priority.equals("high") && !priority.equals("loq")) {
            errors.rejectValue("priority", "priority must be high or low");
        }
    }
}
