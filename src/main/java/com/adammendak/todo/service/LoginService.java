package com.adammendak.todo.service;

import com.adammendak.todo.exception.UserNotInDatabaseException;
import com.adammendak.todo.exception.UserNotLoggedException;
import com.adammendak.todo.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface LoginService {

    Optional<User> getUserFromDB(String email, String password) throws UserNotInDatabaseException;

    String createJwt(String email, String username, Date date) throws UnsupportedEncodingException;

    Map<String, Object> verifyJwtAndGetData(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException,
            UserNotLoggedException;

}
