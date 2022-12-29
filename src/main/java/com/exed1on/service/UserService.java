package com.exed1on.service;

import com.exed1on.dto.UserDTO;
import com.exed1on.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
    List<UserDTO> getAll();

    User findByName(String name);

    void updateProfile(UserDTO dto);
}
