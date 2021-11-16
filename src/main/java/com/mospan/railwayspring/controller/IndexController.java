package com.mospan.railwayspring.controller;


import com.mospan.railwayspring.model.db.User;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//it may have a mapping, and it will
public class IndexController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(IndexController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //functional for recurrent creating of trips
      /*  Timer timer = new Timer();
        Task task = new Task();
        timer.schedule(task, 0,86400000);*/

        if (req.getSession().getAttribute("defaultLocale") == null) {
            req.getSession().setAttribute("defaultLocale", "ua");
        }
        User user = (User) req.getSession().getAttribute("user");


        if (user != null && user.isAdmin()){
            logger.info("redirecting to the trips page");
            resp.sendRedirect(req.getContextPath() + "/stations/1/page");
        } else {
            logger.info("redirecting to the trips page");
            resp.sendRedirect(req.getContextPath() + "/trips");
        }
    }
}
