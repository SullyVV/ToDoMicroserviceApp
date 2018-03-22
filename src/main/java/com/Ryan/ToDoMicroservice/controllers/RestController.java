package com.Ryan.ToDoMicroservice.controllers;

import com.Ryan.ToDoMicroservice.utilities.ToDoValidator;
import com.Ryan.ToDoMicroservice.utilities.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Ryan.ToDoMicroservice.entities.*;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @RequestMapping("/hello")
    public String sayHello() {
        return "hello moto";
    }

    @RequestMapping("/userInOutput")   //jaskson-library     Java Object -> Json message
    public User giveMeAUser() {
        return new User("Mike@tt.com", "mike", "password");
    }
    @RequestMapping("/todoInInput1")
    public String todoInInout1(ToDo todo) {
        return "ToDo Description: " + todo.getDescription() + ". Todo Priority is: " + todo.getPriority();
    }

    @RequestMapping("/todoInInput2")
    public String todoInInout2(@Valid ToDo todo) {
        return "ToDo Description: " + todo.getDescription() + ". Todo Priority is: " + todo.getPriority();
    }

    @RequestMapping("/todoInInput3")
    public String todoInInout3(@Valid ToDo todo, BindingResult result) {
        if(result.hasErrors()) {
            return "I return the error in the format I like: " + result.toString();
        }
        return "ToDo Description: " + todo.getDescription() + ". Todo Priority is: " + todo.getPriority();
    }

    @RequestMapping(value = "/todoInInput4")
    public String todoInInout4(ToDo todo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(todo, result);
        if(result.hasErrors()) {
            return "I return the error in the format I like: " + result.toString();
        }
        return "ToDo Description: " + todo.getDescription() + ". Todo Priority is: " + todo.getPriority();
    }

    @RequestMapping("/todoInInput5")
    public String todoInInout5(@Valid ToDo todo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(todo, result);
        if(result.hasErrors()) {
            return "I return the error in the format I like: " + result.toString();
        }
        return "ToDo Description: " + todo.getDescription() + ". Todo Priority is: " + todo.getPriority();
    }

    @RequestMapping("/exampleUrl")
     public ResponseEntity<JsonResponse> returnMyStandardResponse() {

        return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(new JsonResponse());
    }

}
