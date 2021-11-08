package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.Entity;
import com.mospan.railwayspring.model.Route;

import com.mospan.railwayspring.service.RouteService;
import com.mospan.railwayspring.service.StationService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.LocalTime;

import java.util.List;


@Controller
public class RouteController {
    private static final Logger logger = Logger.getLogger(RouteController.class);

    Entity findModel(String id) {
        try {
            return new RouteService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * PUT /routes/{id}
     * Updates specified route
     */


    protected void update(Entity route, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("updating route " + route.getId());
        ((Route) route).setDepartTime(LocalTime.parse(req.getParameter("depart_time")));
        ((Route) route).setTime(convertTime(req));

        ((Route) route).setStartStation(new StationService().find(req.getParameter("start_station")));
        ((Route) route).setEndStation(new StationService().find(req.getParameter("end_station")));

        ((Route) route).setPrice(Double.parseDouble(req.getParameter("price")));

        new RouteService().update((Route) route);
        resp.sendRedirect(req.getContextPath() + "/routes/1/page");
    }

    /**
     * GET /routes/{id}/edit
     * Displays edit form for given route
     */

    protected void edit(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("redirecting to route edit page");
        req.setAttribute("route", (Route) entity);
        req.setAttribute("stations", new StationService().findAll());
        req.getRequestDispatcher("/view/routes/edit.jsp").forward(req, resp);
    }

    /**
     * POST /routes
     * Save new routes
     */

    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("saving a new route");
        Route route = new Route();

        ((Route) route).setDepartTime(LocalTime.parse(req.getParameter("depart_time")));

        ((Route) route).setTime(convertTime(req));
        ((Route) route).setStartStation(new StationService().find(req.getParameter("start_station")));
        ((Route) route).setEndStation(new StationService().find(req.getParameter("end_station")));
        ((Route) route).setPrice(Double.parseDouble(req.getParameter("price")));
        new RouteService().insert(route);
        resp.sendRedirect(req.getContextPath() + "/routes/1/page");
    }

    /**
     * GET /routes
     * Displays list of routes
     */

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        goToPage(1, req, resp);
    }

    /**
     * DELETE routes/{id}
     * Removes a specified route from db
     */

    protected void delete(Entity route, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("deleting route " + route.getId());
        new RouteService().delete((Route) route);
        resp.sendRedirect(req.getContextPath() + "/routes/1/page");
    }

    /**
     * converting time from days, hours, minutes to seconds format
     */
    private long convertTime(HttpServletRequest req) {
        Long days = (!"".equals(req.getParameter("days"))) ? Long.parseLong(req.getParameter("days")) : 0;
        Long hours = (!"".equals(req.getParameter("hours"))) ? Long.parseLong(req.getParameter("hours")) : 0;
        Long minutes = (!"".equals(req.getParameter("minutes"))) ? Long.parseLong(req.getParameter("minutes")) : 0;

        hours += days * 24;
        minutes += hours * 60;
        return minutes * 60;
    }

    /**
     * GET /routes/{id}/page
     * Displays list of routes for the page {id}
     */

    protected void goToPage(long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to page " + id + " of routes");
        req.setAttribute("time", LocalTime.MIDNIGHT);
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("routes", new RouteService().findAll());

        int size = new RouteService().findAll().size();
        int pages = size % 10 == 0 ? size / 10 : size / 10 + 1;
        req.setAttribute("pages", pages);

        List<Route> routes = (List<Route>) new RouteService().findRecords(id);
        req.setAttribute("routes", routes);
        req.getRequestDispatcher("/view/routes/list.jsp").forward(req, resp);
    }


}
