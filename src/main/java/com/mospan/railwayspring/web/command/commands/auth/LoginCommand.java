package com.mospan.railwayspring.web.command.commands.auth;

import com.mospan.railwayspring.model.User;
import com.mospan.railwayspring.service.UserService;
import com.mospan.railwayspring.util.PasswordEncryptor;
import com.mospan.railwayspring.web.command.Command;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    public RedirectView execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) request.getSession().getAttribute("defaultLocale")));


        String login = request.getParameter("login");
        String password = PasswordEncryptor.hashPassword(request.getParameter("password"));

        UserService userService = new UserService();
        User user = userService.find(login);

        if (user == null || !user.getPassword().equals(password)) {
            logger.info("logging in failure: wrong login or password");

            request.getSession().setAttribute("errorMessage", rb.getString("wrong_login_password"));

            return new RedirectView(request.getContextPath() + "//auth/login");

        } else {
            logger.info("logging in success for user " + user.getLogin());
            request.getSession().setAttribute("user", user);
            System.out.println("path " +  request.getContextPath() + "/trips");

            return new RedirectView(request.getContextPath() + "/trips");
        }
    }
}
