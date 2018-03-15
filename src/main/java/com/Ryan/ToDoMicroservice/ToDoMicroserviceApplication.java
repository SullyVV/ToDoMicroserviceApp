package com.Ryan.ToDoMicroservice;

import com.Ryan.ToDoMicroservice.daos.ToDoDao;
import com.Ryan.ToDoMicroservice.daos.UserDao;
import com.Ryan.ToDoMicroservice.entities.*;
import com.Ryan.ToDoMicroservice.utilities.EncryptionUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ToDoMicroserviceApplication implements CommandLineRunner {

	@Autowired
	UserDao userDao;

	@Autowired
	ToDoDao toDoDao;

	@Autowired
	EncryptionUtils encryptionUtils;
	public static void main(String[] args) {
		SpringApplication.run(ToDoMicroserviceApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("lets fill H2 in-mem database");
		String encryptedPasswd1 = encryptionUtils.encrypt("hello");
		userDao.save(new User("alex@example.com", "alex", encryptedPasswd1));

		String encruptedPasswd2 = encryptionUtils.encrypt("MyPwd");
		userDao.save(new User("franz@example.com", "franz", encruptedPasswd2));
		toDoDao.save(new ToDo(1, "learn microService", "high", "alex@example.com"));
		toDoDao.save(new ToDo(null,"learn microService", "high", "franz@example.com"));
		toDoDao.save(new ToDo(3, "learn microService", "med", "alex@example.com"));
		System.out.println("completes");

	}

}
