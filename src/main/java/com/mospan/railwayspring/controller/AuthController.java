package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.web.command.commands.auth.LoginCommand;
import com.mospan.railwayspring.web.command.commands.auth.LogoutCommand;
import com.mospan.railwayspring.web.command.commands.auth.RegisterCommand;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class);

    @GetMapping(value = "/auth/login")
    public String showLoginPage(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("forwarding to the login page");
        return "forward:/view/auth/login.jsp";
    }

    @GetMapping(value = "/auth/register")
    public String showRegisterPage(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("forwarding to the register page");
        return "forward:/view/auth/register.jsp";
    }

    @PostMapping(value = "/auth/login")
    public RedirectView login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return new LoginCommand().execute(req, resp);
    }

    @PostMapping(value = "/auth/register")
    public RedirectView register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return new RegisterCommand().execute(req, resp);
    }

    @PostMapping(value = "/auth/logout")
    public RedirectView logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return new LogoutCommand().execute(req, resp);
    }
}