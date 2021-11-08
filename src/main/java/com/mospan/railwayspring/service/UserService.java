package com.mospan.railwayspring.service;

import com.mospan.railwayspring.dao.UserDao;
import com.mospan.railwayspring.model.User;

import java.util.Collection;

public class UserService {
    UserDao dao = new UserDao();

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
    public Collection<User> findAll() {
        return dao.findAll();
    }
    public String getEmailSenderData() {
        return dao.getEmailSenderData();
    }
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }
}
