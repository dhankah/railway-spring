package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.*;
import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.model.db.Trip;
import com.mospan.railwayspring.service.RouteService;
import com.mospan.railwayspring.service.StationService;
import com.mospan.railwayspring.service.TicketService;
import com.mospan.railwayspring.service.TripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.*;
import java.util.*;


@Controller
public class TripController {
    private static final Logger logger = Logger.getLogger(TripController.class);


    @Autowired
    TripService tripService;

    @Autowired
    RouteService routeService;

    @Autowired
    StationService stationService;

    @Autowired
    TicketService ticketService;

    /**
     * GET /trips
     * Displays a page for trips search
     */
    @RequestMapping("/trips")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("defaultLocale") == null) {
            req.getSession().setAttribute("defaultLocale", "en");
        }

        req.getSession().setAttribute("date", LocalDate.now());
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("min_date", LocalDate.now());
        req.setAttribute("max_date", LocalDate.now().plusDays(34));

       return "forward:/view/trips/list.jsp";
    }

    /**
     * Displays a page for selecting a ticket for a trip
     */
    @GetMapping("/trips/{id}/choose")
    public String choose(@PathVariable long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("viewing page for seat selection");
        Trip trip = tripService.findById(id);
        List<Seat> seats = new ArrayList<>();
        Collection<Integer> occupied = ticketService.findSeats(trip);

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
        return "forward:/view/trips/select_seat.jsp";
    }

    /**
     * Displays a page with the route info
     */
    @GetMapping("/trips/{id}/route_info")
    public String routeInfo(@PathVariable long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Route route = tripService.findById(id).getRoute();
        logger.info("viewing info about route " + route.getId());
        req.setAttribute("route", route);
        return "forward:/view/routes/route_info.jsp";
    }

    /**
     * GET /trips/{id}/page
     * Displays list of trips for the user's request
     */
    //should deal with absence of id here
    @GetMapping("/trips/{id}/page")
    public String goToPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to page " + " of trips");
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

        req.getSession().setAttribute("date", LocalDate.now());
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("min_date", LocalDate.now());
        req.setAttribute("max_date", LocalDate.now().plusDays(34));


        Collection<Trip> trips = new ArrayList<>();
        Collection<Route> routes = new RouteService().findByStations((req.getParameter("depart_station")), req.getParameter("arrival_station"));



        if (!routes.isEmpty()) {
            for (Route route : routes) {
                logger.info("will look for records now. with route " + route + " date " + req.getParameter("depart_date"));
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
        req.getSession().setAttribute("depart_station", (stationService.find(req.getParameter("depart_station"))).getId());
        req.getSession().setAttribute("arrival_station", (stationService.find(req.getParameter("arrival_station"))).getId());


        req.setAttribute("trips", trips);
        return "forward:/view/trips/list.jsp";
    }
}