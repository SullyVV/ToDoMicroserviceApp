package com.Ryan.ToDoMicroservice.services;

import com.Ryan.ToDoMicroservice.daos.ToDoDao;
import com.Ryan.ToDoMicroservice.entities.ToDo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TodoServiceImpl implements TodoService {
    @Autowired
    ToDoDao toDoDao;
    @Override
    public List<ToDo> getToDos(String email) {
        return toDoDao.findToDoByFkUser(email);
    }

    public ToDo addToDo(ToDo toDo) {
        return toDoDao.save(toDo);
    }

    public void deleteToDo(Integer id) {
        toDoDao.delete(id);
    }
}
