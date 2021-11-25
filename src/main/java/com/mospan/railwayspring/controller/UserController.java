package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.db.Detail;
import com.mospan.railwayspring.model.db.Ticket;
import com.mospan.railwayspring.model.db.User;
import com.mospan.railwayspring.service.TicketService;
import com.mospan.railwayspring.service.UserService;

import com.mospan.railwayspring.util.PasswordEncryptor;
import com.mospan.railwayspring.util.validator.Validator;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


@Controller
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    static Validator validator = new Validator();


    /**
     * PUT /cabinet/{id}
     * Updates user info
     */
    @PostMapping("/cabinet/{id}")
    public RedirectView update(@PathVariable long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new UserService().findById(id);
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

        req.setCharacterEncoding("UTF-8");

        //update password
        if (null != req.getParameter("password")) {
            logger.info("updating password for user " + user.getId());
            if (PasswordEncryptor.hashPassword(req.getParameter("old_password")).equals(new UserService().find((user).getLogin()).getPassword())
                    && req.getParameter("password").equals(req.getParameter("re_password"))) {
                (user).setPassword(PasswordEncryptor.hashPassword(req.getParameter("password")));
                new UserService().update(user);
                logger.info("password for user " + user.getId() + " updated successfully");
                req.getSession().setAttribute("message", rb.getString("password_updated"));

            } else if (!req.getParameter("old_password").equals(new UserService().find((user).getLogin()).getPassword())) {
                logger.info("password update for user " + user.getId() + " failed: wrong old password");

                req.getSession().setAttribute("errorMessage", rb.getString("wrong_old_pass"));


            } else if (!req.getParameter("password").equals(req.getParameter("re_password"))) {
                logger.info("password update for user " + user.getId() + " failed: passwords do not match");

                req.getSession().setAttribute("errorMessage", rb.getString("not_same+passwords"));


            } else {
                    req.getSession().setAttribute("errorMessage", rb.getString("sth_wrong"));

            }
            return new RedirectView(req.getContextPath() + "/cabinet/" + user.getId() +"/change_password");
        }

        //update user info
        logger.info("updating user " + user.getId() + " info");
        User userUpd = new User();
        userUpd.setId(user.getId());
        userUpd.setPassword((user).getPassword());
        userUpd.setLogin(req.getParameter("login"));
        userUpd.setRole(((User)req.getSession().getAttribute("user")).getRole());
        Detail detailUpd = new Detail();
        detailUpd.setId(user.getId());
        detailUpd.setFirstName(req.getParameter("first_name"));

        detailUpd.setLastName(req.getParameter("last_name"));
        detailUpd.setEmail(req.getParameter("email"));
        userUpd.setDetails(detailUpd);



        if (validator.validateUser(user, userUpd).equals("false")) {
            logger.info("info update for user " + user.getId() + " failed: login or email already exists");

                req.getSession().setAttribute("errorMessage", rb.getString("login_email_exists"));

            return new RedirectView(req.getContextPath() + "/cabinet/" + user.getId() + "/edit");
        } else if (validator.validateUser(user, userUpd).equals("no_change")) {
            logger.info("info update for user " + user.getId() + " failed: no changes were made");
            return new RedirectView(req.getContextPath() + "/cabinet");
        }


        req.getSession().setAttribute("message", rb.getString("profile_updated"));

        new UserService().update(userUpd);
        logger.info("info for user " + user.getId() + " updated successfully");
        return new RedirectView(req.getContextPath() + "/cabinet");
    }

    /**
     * GET /cabinet/{id}/edit
     * Displays edit form for the user
     */
    //do sth with unused id
    @GetMapping("/cabinet/{id}/edit")
    public String edit(HttpServletRequest req) throws IOException {
        logger.info("forwarding to user edit page");
        req.setCharacterEncoding("utf8");
        return "forward:/view/cabinet/edit.jsp";
    }


    /**
     * GET /cabinet
     * Displays user's cabinet page
     */
    @GetMapping("/cabinet")
    public String list(HttpServletRequest req) {
        logger.info("forwarding to user cabinet page");
        long id = ((User)req.getSession().getAttribute("user")).getId();
        req.getSession().setAttribute("user", new UserService().findById(id));
        List<Ticket> upcomingTickets = new TicketService().findAllForUser(((User)req.getSession().getAttribute("user"))).get(1);
        List<Ticket> oldTickets = new TicketService().findAllForUser(((User)req.getSession().getAttribute("user"))).get(0);

        req.setAttribute("upcoming_tickets", upcomingTickets);
        req.setAttribute("old_tickets", oldTickets);

       return "forward:/view/cabinet/cabinet.jsp";
    }

    /**
     * DELETE cabinet/{id}
     * Deletes user from db
     */
    //consider taking user from session instead of path
    @PostMapping(value = "cabinet/{id}", params = "_method=delete")
    public RedirectView delete(@PathVariable long id, HttpServletRequest req) throws ServletException, IOException {
        User user = new UserService().findById(id);
        if (!new TicketService().findAllForUser(user).get(1).isEmpty()) {
            ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

            logger.info("deleting user " + user.getId() + " failed: user has trips");

            req.getSession().setAttribute("errorMessage", rb.getString("should_cancel_trip"));

            return new RedirectView(req.getContextPath() + "/cabinet");
        }
        else {
            Collection<Ticket> tickets = new TicketService().findAllForUser(user).get(0);
            for (Ticket ticket : tickets) {
                new TicketService().delete(ticket, false);
            }
        }
        new UserService().delete(user);
        logger.info("user's profile " + user.getId() + " deleted successfully");

        String locale = (String) req.getSession().getAttribute("defaultLocale");

        req.getSession().invalidate();
        req.getSession().setAttribute("defaultLocale", locale);
        return new RedirectView(req.getContextPath() + "/auth/login");
    }

    /**
     * GET /cabinet/{id}/change_password
     * Displays password change form for the user
     */
    //unused id
    @GetMapping("/cabinet/{id}/change_password")
    public String changePassword() throws ServletException, IOException {
        return "forward:/view/cabinet/change_password.jsp";
    }


}