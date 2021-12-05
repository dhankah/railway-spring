package com.mospan.railwayspring.service.sec;

import com.mospan.railwayspring.dao.implementation.UserDaoImpl;
import com.mospan.railwayspring.model.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("com.mospan.railwayspring.dao")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDaoImpl dao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = dao.find(name);
        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
