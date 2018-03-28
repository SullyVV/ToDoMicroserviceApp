package com.Ryan.ToDoMicroservice.services;

import com.Ryan.ToDoMicroservice.entities.ToDo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TodoService {
    List<ToDo> getToDos(String email);

    ToDo addToDo(ToDo toDo);

    void deleteToDo(Integer id);
}
