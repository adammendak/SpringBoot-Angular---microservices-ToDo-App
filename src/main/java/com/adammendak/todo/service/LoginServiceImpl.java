package com.adammendak.todo.service;

import com.adammendak.todo.exception.UserNotInDatabaseException;
import com.adammendak.todo.exception.UserNotLoggedException;
import com.adammendak.todo.model.User;
import com.adammendak.todo.repository.UserRepository;
import com.adammendak.todo.utility.EncryptionUtil;
import com.adammendak.todo.utility.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService{

    private UserRepository userRepository;
    private EncryptionUtil encryptionUtil;
    private JwtUtils jwtUtils;

    public LoginServiceImpl(UserRepository userRepository, EncryptionUtil encryptionUtil, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encryptionUtil = encryptionUtil;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Optional<User> getUserFromDB(String email, String password) throws UserNotInDatabaseException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        log.info("user from db is present {}", userOptional.isPresent());
        if( userOptional.isPresent()) {
            User user = userOptional.get();
            log.info("there is a user {}", user.toString());
            if(!encryptionUtil.decrypt(user.getPassword()).equals(password)) {
                throw new UserNotInDatabaseException("wrong password");
            }
        } else {
            throw new UserNotInDatabaseException("wrong email or password");
        }
        return userOptional;
    }

    @Override
    public String createJwt(String email, String username, Date date) throws UnsupportedEncodingException {
        date.setTime(date.getTime() + (300 * 1000));
        return jwtUtils.generateJwt(email, username, date);
    }

    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, UserNotLoggedException {
       String jwt = jwtUtils.getJwtFromHttpRequest(httpServletRequest);
       if(jwt == null) {
           throw new UserNotLoggedException("User not logged, log in first");
       }
       return jwtUtils.jwt2Map(jwt);
    }
}
