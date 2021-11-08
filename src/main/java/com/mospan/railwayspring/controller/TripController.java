package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.*;
import com.mospan.railwayspring.service.RouteService;
import com.mospan.railwayspring.service.StationService;
import com.mospan.railwayspring.service.TicketService;
import com.mospan.railwayspring.service.TripService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.*;
import java.util.*;


@Controller
public class TripController extends ResourceController {
    private static final Logger logger = Logger.getLogger(TripController.class);
    @Override
    Entity findModel(String id) {
        try {
            return new TripService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * GET /trips
     * Displays a page for trips search
     */
    @RequestMapping("/trips")
    public String lost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("defaultLocale") == null) {
            req.getSession().setAttribute("defaultLocale", "en");
        }
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));
        System.out.println("hehehehehhehehheheh" + req.getSession().getAttribute("defaultLocale"));
        System.out.println(rb.getString("login"));
        req.getSession().setAttribute("date", LocalDate.now());
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("min_date", LocalDate.now());
        req.setAttribute("max_date", LocalDate.now().plusDays(34));

       return "/view/trips/list.jsp";
    }

    /**
     * Displays a page for selecting a ticket for a trip
     */
    @Override
    protected void choose(Entity trip, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("viewing page for seat selection");

        List<Seat> seats = new ArrayList<>();
        TicketService ticketService = new TicketService();
        Collection<Integer> occupied = ticketService.findSeats((Trip) trip);

        for (int i = 1; i < 37; i++) {
            Seat seat = new Seat();
            seat.setNumber(i);
            if(occupied.contains(i)) {
                seat.setOccupied(true);
            }
            seats.add(seat);
        }
        req.setAttribute("trip", trip);
        req.setAttribute("seats", seats);
        req.getRequestDispatcher("/view/trips/select_seat.jsp").forward(req, resp);;
    }

    /**
     * Displays a page with the route info
     */
    @Override
    protected void routeInfo(Entity trip, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("viewing info about route " + ((Trip)trip).getRoute().getId());
        req.setAttribute("route", ((Trip)trip).getRoute());
        req.getRequestDispatcher("/view/routes/route_info.jsp").forward(req, resp);
    }

    /**
     * GET /trips/{id}/page
     * Displays list of trips for the user's request
     */
    @Override
    protected void goToPage(long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to page " + " of trips");
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

        req.getSession().setAttribute("date", LocalDate.now());
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("min_date", LocalDate.now());
        req.setAttribute("max_date", LocalDate.now().plusDays(34));


        Collection<Trip> trips = new ArrayList<>();
        Collection<Route> routes = new RouteService().findByStations((req.getParameter("depart_station")), req.getParameter("arrival_station"));

        if (routes != null) {
            for (Route route : routes) {
                Collection<Trip> tripsForRoute = new TripService().findRecords(route, LocalDate.parse(req.getParameter("depart_date")));
                if (tripsForRoute != null) {
                    for (Trip trip : tripsForRoute) {
                        //trip is ignored if the train has departed before te time of request
                       if (trip.getDepartDate().isEqual(LocalDate.now()) && trip.getRoute().getDepartTime().isBefore(LocalTime.now())) {
                           continue;
                       }
                       trips.add(trip);
                    }

                    req.getSession().setAttribute("trips", trips);
                }
            }
            if (trips.isEmpty()) {
                logger.info("found no trips for user's request");
                req.getSession().removeAttribute("trips");

                req.getSession().setAttribute("errorMessage", rb.getString("no_trains_error"));
            } else {
                logger.info("search for trips was successful");
            }
        } else {
            req.getSession().removeAttribute("trips");
            logger.info("found no route for user's request");

            req.getSession().setAttribute("errorMessage", rb.getString("no_route_error"));


        }

        req.getSession().setAttribute("date", req.getParameter("depart_date"));
        req.getSession().setAttribute("depart_station", (new StationService().find(req.getParameter("depart_station"))).getId());
        req.getSession().setAttribute("arrival_station", (new StationService().find(req.getParameter("arrival_station"))).getId());


        req.setAttribute("trips", trips);
        req.getRequestDispatcher("/view/trips/list.jsp").forward(req, resp);
    }
}