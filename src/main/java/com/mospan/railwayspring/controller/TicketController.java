package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.*;
import com.mospan.railwayspring.service.StationService;
import com.mospan.railwayspring.service.TicketService;
import com.mospan.railwayspring.service.TripService;
import com.mospan.railwayspring.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class TicketController {
    private static final Logger logger = Logger.getLogger(TicketController.class);


    /**
     * POST /tickets
     * Save new tickets
     */
    @PostMapping("/tickets")
    public RedirectView store(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("purchasing ticket");
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

        User user = (User) req.getSession().getAttribute("user");

        double price = (new TripService().findById(Long.parseLong(req.getParameter("trip"))).getRoute().getPrice());

        //user does not have enough money
        if (user.getBalance() < price) {
            logger.info("purchasing ticket failed: user does not have enough money");
            req.getSession().setAttribute("errorMessage", rb.getString("not_enough"));

            return new RedirectView(req.getHeader("Referer"));
        }

        Collection<Ticket> tickets = (new TicketService().findAllForUser(user.getId()).get(1));
        if (null != tickets) {
            for (Ticket ticket : tickets) {
                Trip trip = new TripService().findById(Long.parseLong(req.getParameter("trip")));
                //user already has a ticket for the trip
                if (ticket.getTrip().getId() == trip.getId()) {
                    logger.info("purchasing ticket failed: user already has ticket on this trip");

                    req.getSession().setAttribute("errorMessage", rb.getString("ticket_error"));

                    return new RedirectView(req.getContextPath() + "/trips/" + trip.getId() + "/choose");
                }
            }
        }
        //user has no obstacles for buying a ticket
        Ticket ticket = new Ticket();
        ticket.setUser((User) req.getSession().getAttribute("user"));
        Trip trip = new TripService().findById(Long.parseLong(req.getParameter("trip")));
        trip.setAvailablePlaces(trip.getAvailablePlaces()-1);
        new TripService().update(trip);
        ticket.setTrip(trip);
        ticket.setSeat(Integer.parseInt(req.getParameter("number")));
        user.setBalance(user.getBalance() - price);
        new UserService().update(user);
        new TicketService().insert(ticket);
        logger.info("ticket purchased successfully");
        return new RedirectView(req.getContextPath() + "/cabinet");
    }

    /**
     * GET /tickets
     * Displays list of tickets
     */
    @GetMapping("/tickets")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("viewing tickets");
        List<Ticket> upcomingTickets = ((List<Ticket>) new TicketService().findAllForUser(((User)req.getSession().getAttribute("user")).getId()).get(1));
        List<Ticket> oldTickets = ((List<Ticket>) new TicketService().findAllForUser(((User)req.getSession().getAttribute("user")).getId()).get(1));

        req.setAttribute("upcoming_tickets", upcomingTickets);
        req.setAttribute("old_tickets", oldTickets);

        return"/view/cabinet.jsp";
    }

    /**
     * DELETE tickets/{id}
     * Removes a specified ticket from db
     */
    @PostMapping(value = "tickets/{id}", params = "_method=delete")
    protected RedirectView delete(@PathVariable long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Ticket ticket = new TicketService().findById(id);
        logger.info("deleting ticket " + ticket.getId());

        if ((ticket.getTrip().getDepartDate().isAfter(LocalDate.now()) ||
                ticket.getTrip().getDepartDate().isEqual(LocalDate.now())
                        && ticket.getTrip().getRoute().getDepartTime().isAfter(LocalTime.now()))) {
            User user = (User) req.getSession().getAttribute("user");
            user.setBalance(user.getBalance() + ticket.getTrip().getRoute().getPrice());
            new UserService().update(user);
        }
        new TicketService().delete(ticket, true);
        return new RedirectView(req.getContextPath() + "/cabinet");
    }




}
