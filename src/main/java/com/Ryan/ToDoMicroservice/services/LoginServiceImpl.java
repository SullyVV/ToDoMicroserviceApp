package com.Ryan.ToDoMicroservice.services;

import com.Ryan.ToDoMicroservice.daos.ToDoDao;
import com.Ryan.ToDoMicroservice.daos.UserDao;
import com.Ryan.ToDoMicroservice.utilities.EncryptionUtils;
import com.Ryan.ToDoMicroservice.utilities.JwtUtils;
import com.Ryan.ToDoMicroservice.utilities.UserNotInDatabaseException;
import com.Ryan.ToDoMicroservice.utilities.UserNotLoggedException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import com.Ryan.ToDoMicroservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EncryptionUtils encryptionUtils;

    @Override
    public Optional<User> getUserFromDb(String email, String pswd) throws UserNotInDatabaseException {
        Optional<User> userr =userDao.findUserByEmail(email);
        if (userr.isPresent()) {
            User user = userr.get();
            if (!encryptionUtils.decrypt(user.getPasswd()).equals(pswd)) {
                throw new UserNotInDatabaseException("wrong email or password");
            }
        } else {
            throw new UserNotInDatabaseException("Wrong email or password");
        }
         return userr;
    }

    @Override
    public String createJwt(String email, String name, Date date) throws UnsupportedEncodingException {
        date.setTime(date.getTime() + (300 + 1000));
        return jwtUtils.generateJwt(email, name, date);
    }

    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedException {
        String jwt = jwtUtils.getJwtFromHttpRequest(request);
        if (jwt == null) {
            throw new UserNotLoggedException("User not logged in first");
        }
        return jwtUtils.jwt2Map(jwt);
    }
}
