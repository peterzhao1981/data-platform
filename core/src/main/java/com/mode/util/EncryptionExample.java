package com.mode.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Created by chao on 12/02/2017.
 */
public class EncryptionExample {

    //private static String cryptoPassword = "babeeq.com";
    private static String cryptoPassword = "test.babeeq.com";
//    private static String cryptoPassword = "prod.babeeq.com";

    private StandardPBEStringEncryptor encryptor;

    public EncryptionExample() {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(cryptoPassword);
        System.out.println("Initialize Encryptor with password : " + cryptoPassword);
    }

    public static void main(String[] args) {
        String[] values = {"root", "UxrsisqXzSANyikJxmNvB/bTLCKJ+9x7T/Kpm1tG",
                "babeeq-jwt-signing-key", "key-17ec36512d7d417dc5ee383a98f6f3c9",
                " tJYG6OxdB0qThAqH2v2B2UHyK3SUhc57rBZfAgyb",
                "dNm_2AF2T3mqjVuboQkyMA"};

        EncryptionExample example = new EncryptionExample();
        for (String value : values) {
            final String encryptedValue = example.encrypt(value);
            final String decryptedValue = example.decrypt(encryptedValue);
            System.out.println("Original Value : " + value
                    + ", Encrypted Value : " + encryptedValue
                    + ", Decrypted Value : " + decryptedValue);
        }
    }

    public String encrypt(String value) {
        return encryptor.encrypt(value);
    }

    public String decrypt(String encryptedValue) {
        return encryptor.decrypt(encryptedValue);
    }
}
