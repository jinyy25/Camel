package com.example.test.camel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GmdCrypto {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private SecretKey secretKey;

    public GmdCrypto() {
    }

    @PostConstruct
    private void init() throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            // KeyGenerator로 AES 키 생성
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // 128-bit AES
            this.secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException var2) {
            this.log.error("Init AES Crypto failed.", var2);
            throw var2;
        }
    }

    public String encrypt(String str) throws InvalidKeyException {
        try {
            // Cipher 객체 생성 및 AES 암호화 방식 설정
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            byte[] encryptedBytes = cipher.doFinal(str.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            this.log.error("Encryption failed.", e);
            throw new InvalidKeyException("Encryption failed", e);
        }
    }

    public String decrypt(String str) throws InvalidKeyException {
        try {
            // Cipher 객체 생성 및 AES 복호화 방식 설정
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(str);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            this.log.error("Decryption failed", e);
            throw new InvalidKeyException("Decryption failed", e);
        }
    }
}
