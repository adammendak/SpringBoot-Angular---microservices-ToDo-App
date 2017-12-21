package com.adammendak.todo.utility;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@PropertySource("classpath:application.properties")
public class EncryptionUtil {

    private static BasicTextEncryptor basicTextEncryptor;

    @Value("${encryption.salt}")
    private String salt;

    public EncryptionUtil() {
        this.basicTextEncryptor = new BasicTextEncryptor();
    }

    @PostConstruct
    public void init() {
        basicTextEncryptor.setPassword(salt);
    }

    public String encrypt( String data) {
        return basicTextEncryptor.encrypt(data);
    }

    public String decrypt (String data) {
        return basicTextEncryptor.decrypt(data);
    }
}
