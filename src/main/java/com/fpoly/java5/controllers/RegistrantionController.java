package com.fpoly.java5.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fpoly.java5.beans.UserBean;
import com.fpoly.java5.jpas.UserJPA;
import com.fpoly.java5.services.UserService;

import jakarta.validation.Valid;

@Controller
public class RegistrantionController {

    @Autowired
    UserJPA userJPA;

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        UserBean userBean = new UserBean();
        model.addAttribute("user", userBean);
        return "user/register.html"; 
    }

    @PostMapping("/register")
    public String handleAddUser(@Valid @ModelAttribute("user") UserBean userBean, Errors errors, Model model) {
        String avatarError = userBean.isAvatarError();
        if (avatarError != null) {
            errors.rejectValue("avatar", "error.avatar", avatarError);
        }
        
        if (!userBean.isPasswordMatch()) {
            errors.rejectValue("confirmPassword", "error.confirmPassword", "Mật khẩu xác nhận không khớp");
        }

        if (errors.hasErrors()) {
            return "user/register";
        }

        String result = userService.insertUser(userBean);

        if (result == null) {
            return "redirect:/login";
        }

        model.addAttribute("error", result);
        return "user/register.html";
    }
}