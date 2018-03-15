package com.Ryan.ToDoMicroservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Ryan.ToDoMicroservice.entities.*;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    // name stratege
    Optional<User> findUserByEmail(String email);

    // query annotation
    @Query(value="select * from users WHERE email=:email", nativeQuery = true)
    Optional<User> findUserByTheEmail(String email);

    // native method
    User findOne(String email);

}
