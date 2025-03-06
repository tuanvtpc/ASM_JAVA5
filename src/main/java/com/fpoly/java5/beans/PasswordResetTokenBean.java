package com.fpoly.java5.beans;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PasswordResetTokenBean {
	 private Map<String, String> tokenMap = new HashMap<>();

	 
	    // Lưu token vào bộ nhớ tạm
	    public void saveToken(String token, String email) {
	        tokenMap.put(token, email); // Lưu token cùng với email của người dùng
	    }

	    // Lấy email từ token
	    public String getEmailByToken(String token) {
	        return tokenMap.get(token);
	    }

	    // Xóa token sau khi sử dụng
	    public void removeToken(String token) {
	        tokenMap.remove(token);
	    }
}
