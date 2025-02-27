package com.fpoly.java5.services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;



@Service
public class EncryptionService {
	private static final String ALGORITHM = "AES";

    // Tạo khóa AES từ một chuỗi bất kỳ
    private static SecretKeySpec generateKey(String secret) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(secret.getBytes(StandardCharsets.UTF_8));
            key = Arrays.copyOf(key, 16); // Chỉ lấy 16 byte đầu tiên (128 bit)
            return new SecretKeySpec(key, ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo khóa", e);
        }
    }

    // Mã hóa thông tin
    public String encrypt(String data) {
        try {
            SecretKeySpec secretKey = generateKey("your-secret-key-123");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi mã hóa dữ liệu", e);
        }
    }

    // Giải mã thông tin
    public String decrypt(String encryptedData) {
        try {
            SecretKeySpec secretKey = generateKey("your-secret-key-123");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi giải mã dữ liệu", e);
        }
    }
}
