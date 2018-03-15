package com.Ryan.ToDoMicroservice.daos;

import com.Ryan.ToDoMicroservice.entities.ToDo;
import com.Ryan.ToDoMicroservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ToDoDao extends JpaRepository<ToDo, Integer> {
    // name stratege
    List<User> findToDoByFkUser(String email);

    // query annotation
    @Query(value="select * from todos WHERE description=:description", nativeQuery = true)
    Optional<ToDo> findToDoByDescription(@Param("description") String description);

    // native method
    ToDo findOne(Integer Id);

}
