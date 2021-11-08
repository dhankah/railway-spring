package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.*;
import com.mospan.railwayspring.service.StationService;
import com.mospan.railwayspring.service.TicketService;
import com.mospan.railwayspring.service.TripService;
import com.mospan.railwayspring.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet (value = "/tickets/*")
public class TicketController extends ResourceController{
    private static final Logger logger = Logger.getLogger(TicketController.class);
    @Override
    Entity findModel(String id) {
        try {
            return new TicketService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * POST /tickets
     * Save new tickets
     */
    @Override
    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("purchasing ticket");
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

        User user = (User) req.getSession().getAttribute("user");

        double price = (new TripService().findById(Long.parseLong(req.getParameter("trip"))).getRoute().getPrice());

        //user does not have enough money
        if (user.getBalance() < price) {
            logger.info("purchasing ticket failed: user does not have enough money");
            req.getSession().setAttribute("errorMessage", rb.getString("not_enough"));

            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        Collection<Ticket> tickets = (new TicketService().findAllForUser(user.getId()).get(1));
        if (null != tickets) {
            for (Ticket ticket : tickets) {
                Trip trip = new TripService().findById(Long.parseLong(req.getParameter("trip")));
                //user already has a ticket for the trip
                if (ticket.getTrip().getId() == trip.getId()) {
                    logger.info("purchasing ticket failed: user already has ticket on this trip");

                    req.getSession().setAttribute("errorMessage", rb.getString("ticket_error"));

                    resp.sendRedirect(req.getContextPath() + "/trips/" + trip.getId() + "/choose");
                    return;
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
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }

    /**
     * GET /tickets
     * Displays list of tickets
     */
    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("viewing tickets");
        List<Ticket> upcomingTickets = ((List<Ticket>) new TicketService().findAllForUser(((User)req.getSession().getAttribute("user")).getId()).get(1));
        List<Ticket> oldTickets = ((List<Ticket>) new TicketService().findAllForUser(((User)req.getSession().getAttribute("user")).getId()).get(1));

        req.setAttribute("upcoming_tickets", upcomingTickets);
        req.setAttribute("old_tickets", oldTickets);

        req.getRequestDispatcher("/view/cabinet.jsp").forward(req, resp);
    }

    /**
     * DELETE tickets/{id}
     * Removes a specified ticket from db
     */
    @Override
    protected void delete(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("deleting ticket " + entity.getId());
        Ticket ticket = (Ticket) entity;
        if ((ticket.getTrip().getDepartDate().isAfter(LocalDate.now()) ||
                ticket.getTrip().getDepartDate().isEqual(LocalDate.now())
                        && ticket.getTrip().getRoute().getDepartTime().isAfter(LocalTime.now()))) {
            User user = (User) req.getSession().getAttribute("user");
            user.setBalance(user.getBalance() + ticket.getTrip().getRoute().getPrice());
            new UserService().update(user);
        }
        new TicketService().delete(ticket, true);
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }




}
