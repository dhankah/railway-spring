package com.mospan.railwayspring.dao.interfaces;

import com.mospan.railwayspring.model.db.User;


public interface UserDao {
    void insert(User user);
    void delete(User user);
    void update(User user);
    User find(String login);
    User findByEmail(String email);
    User findById(long id);
    String getEmailSenderData();
}
