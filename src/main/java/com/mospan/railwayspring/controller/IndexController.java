package com.mospan.railwayspring.controller;


import com.mospan.railwayspring.model.db.User;

import com.mospan.railwayspring.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@ComponentScan
public class IndexController  {
    private static final Logger logger = Logger.getLogger(IndexController.class);

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public RedirectView doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("index controller is called");
        //functional for recurrent creating of trips
      /*Timer timer = new Timer();
        Task task = new Task();
        timer.schedule(task, 0,86400000);*/
        String username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
            long id = userService.find(username).getId();
            req.getSession().setAttribute("user", userService.findById(id));
            User user = (User) req.getSession().getAttribute("user");
            if (user != null && user.isAdmin()){
                logger.info("redirecting to the stations page");
                return new RedirectView(req.getContextPath() + "/stations/1/page");
            } else {
                logger.info("redirecting to the trips page");
                return new RedirectView(req.getContextPath() + "/trips");
            }
        } else {
            logger.info("redirecting to the trips page");
            return new RedirectView(req.getContextPath() + "/trips");
        }
    }

}
