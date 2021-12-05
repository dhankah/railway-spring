package com.mospan.railwayspring.command;

import com.mospan.railwayspring.model.db.Detail;
import com.mospan.railwayspring.model.Role;
import com.mospan.railwayspring.model.db.User;
import com.mospan.railwayspring.service.UserService;
import com.mospan.railwayspring.util.PasswordEncryptor;
import com.mospan.railwayspring.util.validator.Validator;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegisterCommand.class);


    Validator validator = new Validator();

    @Override
    public RedirectView execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("starting registering");

        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) request.getSession().getAttribute("defaultLocale")));


        User user = new User();
        user.setLogin(request.getParameter("login"));

        user.setPassword(PasswordEncryptor.hashPassword(request.getParameter("password")));
        Detail detail = new Detail();
        detail.setFirstName(request.getParameter("first_name"));
        detail.setLastName(request.getParameter("last_name"));
        detail.setEmail(request.getParameter("email"));

        user.setDetails(detail);
        user.setRole(Role.CLIENT);

        if (!validator.validateRegisterUser(user)) {
            logger.info("register failed: login or email already exists");
                request.getSession().setAttribute("errorMessage", rb.getString("login_email_exists"));

            return new RedirectView(request.getContextPath() + "/auth/register");
        }
        UserService userService = new UserService();
        userService.insert(user);
        logger.info("register success");
        return new RedirectView(request.getContextPath() + "/auth/login");
    }
}
