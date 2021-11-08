package com.mospan.railwayspring.controller;
import com.mospan.railwayspring.model.User;
import com.mospan.railwayspring.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/top_up/*"})
public class TopUpController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TopUpController.class);

    /**
     * GET /top_up
     * Displays topUp account form for user
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to top_up page");
        req.getRequestDispatcher("/view/cabinet/top_up.jsp").forward(req, resp);
    }

    /**
     * POST /top_up
     * tops user's account up
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("topping balance up for user " + req.getSession().getAttribute("user"));
        User user = (User) req.getSession().getAttribute("user");
        user.setBalance(user.getBalance() + Double.parseDouble(req.getParameter("amount_to_add")));
        new UserService().update(user);
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }
}
