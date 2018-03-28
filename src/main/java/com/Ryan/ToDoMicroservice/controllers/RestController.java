package com.Ryan.ToDoMicroservice.controllers;

import com.Ryan.ToDoMicroservice.services.LoginService;
import com.Ryan.ToDoMicroservice.services.TodoService;
import com.Ryan.ToDoMicroservice.utilities.ToDoValidator;
import com.Ryan.ToDoMicroservice.utilities.JsonResponseBody;
import com.Ryan.ToDoMicroservice.utilities.UserNotInDatabaseException;
import com.Ryan.ToDoMicroservice.utilities.UserNotLoggedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Ryan.ToDoMicroservice.entities.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    LoginService loginService;

    @Autowired
    TodoService todoService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String pwd) {
        // 1. success: return a string with the login msg + jwt in the header of the http response
        // 2. failure: return an error msg
        try {
            Optional<User> userr = loginService.getUserFromDb(email, pwd);
            User user = userr.get();
            String jwt = loginService.createJwt(email, user.getName(), new Date());
            // the only case in which server sends the jwt to the client
            return ResponseEntity.status(HttpStatus.OK).header("jwt",jwt).body(new JsonResponseBody(HttpStatus.OK.value(), "Success!"));
        } catch (UserNotInDatabaseException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden"));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request"));
        }

    }

    @RequestMapping(value = "/showToDos")
    public ResponseEntity<JsonResponseBody> showToDos(HttpServletRequest request) {
        // 1. success:  return a list of ToDos in the "response" attribute of the JsonResponseBody
        // 2. failure: return an error msg

        try {
            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), todoService.getToDos((String)userData.get("email"))));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request"));
        } catch (UserNotLoggedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden"));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Gateway Timeout"));
        }
    }

    @RequestMapping(value = "/newToDos", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest request, @Valid ToDo toDo, BindingResult result) {
        // 1. success: todoinserted into the response attribute of the json response body
        // 2. failure: error msg
        ToDoValidator toDoValidator = new ToDoValidator();
        toDoValidator.validate(toDo, result);
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "DATA not valid: " + result.toString()));
        }
        try {
            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), todoService.addToDo(toDo)));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request"));
        } catch (UserNotLoggedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden"));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Gateway Timeout"));
        }
    }

    @RequestMapping(value = "/deleteToDos/{id}")
    public ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Integer toDoId) {
        // 1. success: msg of success
        // 2. failure: error msg
        try {
            loginService.verifyJwtAndGetData(request);
            todoService.deleteToDo(toDoId);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "success!"));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request"));
        } catch (UserNotLoggedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden"));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Gateway Timeout"));
        }

    }

}
