package com.fpoly.java5.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.UserJPA;
import com.fpoly.java5.services.UserService;

@Controller
public class ForgotPasswordController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserJPA userJPA;

    // Hiển thị form quên mật khẩu
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "/user/forgot-password.html";
    }

    // Xử lý yêu cầu quên mật khẩu
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        // Bắt lỗi bỏ trống
        if (email.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email không được bỏ trống.");
            return "redirect:/forgot-password";
        }

        // Bắt lỗi định dạng email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            redirectAttributes.addFlashAttribute("error", "Định dạng email không hợp lệ.");
            return "redirect:/forgot-password";
        }

        // Kiểm tra email có tồn tại trong hệ thống không
        Optional<UserEntity> userOptional = userJPA.findByEmail(email);
        if (!userOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email không tồn tại trong hệ thống.");
            return "redirect:/forgot-password";
        }

        // Gửi mã token đặt lại mật khẩu qua email
        userService.createPasswordResetTokenForUser(email);
        redirectAttributes.addFlashAttribute("message", "Chúng tôi đã gửi mã xác nhận đến email của bạn.");
        return "redirect:/reset-password";
    }

    // Hiển thị form đặt lại mật khẩu
    @GetMapping("/reset-password")
    public String showResetPasswordForm() {
        return "/user/reset-password.html";
    }

    // Xử lý yêu cầu đặt lại mật khẩu
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String userToken,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       RedirectAttributes redirectAttributes) {
        // Bắt lỗi bỏ trống
        if (userToken.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            return "redirect:/reset-password";
        }

        // Bắt lỗi mật khẩu không khớp
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu và xác nhận mật khẩu không khớp.");
            return "redirect:/reset-password";
        }

        // Kiểm tra token hợp lệ
        if (userService.validatePasswordResetToken(userToken, password)) {
            redirectAttributes.addFlashAttribute("message", "Mật khẩu của bạn đã được đặt lại thành công.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Mã xác nhận không hợp lệ hoặc đã hết hạn.");
            return "redirect:/reset-password";
        }
    }
}
