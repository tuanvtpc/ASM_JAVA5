package com.fpoly.java5.beans;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {
	private Optional<Integer> id;
	
    @NotBlank(message = "Họ và tên không được để trống")
    private String name;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword; // Thêm trường xác nhận mật khẩu

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    private MultipartFile avatar;

    public String isAvatarError() {
        if (avatar == null || avatar.isEmpty()) {
            return "Vui lòng chọn ảnh đại diện";
        }

        double size = avatar.getSize() / 1024.0 / 1024.0; // Chuyển đổi sang MB
        if (size > 20) {
            return "Kích thước ảnh không được vượt quá 20MB";
        }
        return null;
    }

    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}