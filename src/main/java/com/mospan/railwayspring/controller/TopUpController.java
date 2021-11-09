package com.mospan.railwayspring.controller;
import com.mospan.railwayspring.model.User;
import com.mospan.railwayspring.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class TopUpController {

    private static final Logger logger = Logger.getLogger(TopUpController.class);
    /**
     * GET /top_up
     * Displays topUp account form for user
     */
    @GetMapping("/top_up")
    public String topUpPage() {
        logger.info("forwarding to top_up page");
        return "/view/cabinet/top_up.jsp";
    }

    /**
     * POST /top_up
     * tops user's account up
     */
    @PostMapping("/top_up")
    public RedirectView topUp(HttpServletRequest req) {
        logger.info("topping balance up for user " + req.getSession().getAttribute("user"));
        User user = (User) req.getSession().getAttribute("user");
        user.setBalance(user.getBalance() + Double.parseDouble(req.getParameter("amount_to_add")));
        new UserService().update(user);
       return new RedirectView(req.getContextPath() + "/cabinet");
    }
}
