package com.mospan.railwayspring.service;

import com.mospan.railwayspring.dao.implementation.UserDaoImpl;
import com.mospan.railwayspring.dao.interfaces.UserDao;
import com.mospan.railwayspring.model.db.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    UserDao dao = new UserDaoImpl();

    public void insert(User user) {
        dao.insert(user);
    }
    public void update(User user) {
        dao.update(user);
    }
    public User find(String login) {
        return dao.find(login);
    }
    public User findById(long id) {
        return dao.findById(id);
    }
    public void delete(User user) {
        dao.delete(user);
    }
    public String getEmailSenderData() {
        return dao.getEmailSenderData();
    }
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }
}
