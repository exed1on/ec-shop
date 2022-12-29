package com.exed1on.controller;

import com.exed1on.dto.UserDTO;
import com.exed1on.model.User;
import com.exed1on.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String userList(Model model){
        model.addAttribute("users",userService.getAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user",new UserDTO());
        return "user";
    }

    @PostMapping("/new")
    public String saveUser(UserDTO userDTO, Model model){
        if(userService.save(userDTO)){
            return "redirect:/users";
        } else {
            model.addAttribute("user", userDTO);
            return "user";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if(principal == null){
            throw new RuntimeException("You are not authorize");
        }
        User user = userService.findByName(principal.getName());

        UserDTO dto = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", dto);
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal){
        if(principal == null
                || !Objects.equals(principal.getName(), dto.getUsername())){
            throw new RuntimeException("You are not authorize");
        }
        if(dto.getPassword() != null
                && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())){
            model.addAttribute("user", dto);
            return "profile";
        }

        userService.updateProfile(dto);
        return "redirect:/users/profile";
    }
}
