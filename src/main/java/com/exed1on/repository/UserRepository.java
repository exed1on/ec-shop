package com.exed1on.repository;


import com.exed1on.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   User findFirstByName(String name);
}
