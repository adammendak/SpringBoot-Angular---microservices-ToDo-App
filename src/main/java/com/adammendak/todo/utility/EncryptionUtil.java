package com.adammendak.todo.utility;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptionUtil {

    private BasicTextEncryptor basicTextEncryptor;

    public EncryptionUtil(BasicTextEncryptor basicTextEncryptor) {
        this.basicTextEncryptor = basicTextEncryptor;
        basicTextEncryptor.setPassword("secretPassword");
    }

    public String encrypt( String data) {
        return basicTextEncryptor.encrypt(data);
    }

    public String decrypt (String data) {
        return basicTextEncryptor.decrypt(data);
    }
}
