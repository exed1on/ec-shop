package com.exed1on.controller;

import com.exed1on.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {
    @GetMapping("/signup")
    public String addUser(Model model){
        model.addAttribute("user",new UserDTO());
        return "signup";
    }
}
