package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.db.Route;

import com.mospan.railwayspring.service.RouteService;
import com.mospan.railwayspring.service.StationService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.LocalTime;

import java.util.List;

@Controller
@ComponentScan
public class RouteController {
    private static final Logger logger = Logger.getLogger(RouteController.class);

    @Autowired
    RouteService routeService;

    @Autowired
    StationService stationService;

    /**
     * PUT /routes/{id}
     * Updates specified route
     */
    @PostMapping("/routes/{id}")
    public RedirectView update(@PathVariable long id, HttpServletRequest req) {
        Route route = routeService.findById(id);
        logger.info("updating route " + route.getId());
        route.setDepartTime(LocalTime.parse(req.getParameter("depart_time")));
        route.setTime(convertTime(req));
        route.setStartStation(stationService.find(req.getParameter("start_station")));
        route.setEndStation(stationService.find(req.getParameter("end_station")));
        route.setPrice(Double.parseDouble(req.getParameter("price")));
        routeService.update(route);
        return new RedirectView(req.getContextPath() + "/routes/1/page");

    }

    /**
     * GET /routes/{id}/edit
     * Displays edit form for given route
     */
    @GetMapping("/routes/{id}/edit")
    public String edit(@PathVariable long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Route route = routeService.findById(id);
        logger.info("redirecting to route edit page");
        req.setAttribute("route", route);
        req.setAttribute("stations", new StationService().findAll());
        return "forward:/view/routes/edit.jsp";
    }

    /**
     * POST /routes
     * Save new routes
     */

    @PostMapping("routes")
    public RedirectView store(HttpServletRequest req) {
        logger.info("saving a new route");
        Route route = new Route();

        route.setDepartTime(LocalTime.parse(req.getParameter("depart_time")));

        route.setTime(convertTime(req));
        route.setStartStation(stationService.find(req.getParameter("start_station")));
        route.setEndStation(stationService.find(req.getParameter("end_station")));
        route.setPrice(Double.parseDouble(req.getParameter("price")));
        routeService.insert(route);
        return new RedirectView(req.getContextPath() + "/routes/1/page");
    }

    /**
     * GET /routes
     * Displays list of routes
     */
    @GetMapping("/routes/{id}/page")
    public void list(@PathVariable long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        goToPage(id, req, resp);
    }

    /**
     * DELETE routes/{id}
     * Removes a specified route from db
     */
    @PostMapping(value="routes/{id}", params ="_method=delete")
    public RedirectView delete(@PathVariable long id, HttpServletRequest req) {
        Route route = routeService.findById(id);
        logger.info("deleting route " + route.getId());
        routeService.delete(route);
        return new RedirectView(req.getContextPath() + "/routes/1/page");
    }

    /**
     * converting time from days, hours, minutes to seconds format
     */
    private long convertTime(HttpServletRequest req) {
        long days = (!"".equals(req.getParameter("days"))) ? Long.parseLong(req.getParameter("days")) : 0;
        long hours = (!"".equals(req.getParameter("hours"))) ? Long.parseLong(req.getParameter("hours")) : 0;
        long minutes = (!"".equals(req.getParameter("minutes"))) ? Long.parseLong(req.getParameter("minutes")) : 0;

        hours += days * 24;
        minutes += hours * 60;
        return minutes * 60;
    }

    /**
     * GET /routes/{id}/page
     * Displays list of routes for the page {id}
     */
    public void goToPage(long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to page " + id + " of routes");
        req.setAttribute("time", LocalTime.MIDNIGHT);
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("routes", new RouteService().findAll());

        int size = new RouteService().findAll().size();
        int pages = size % 5 == 0 ? size / 5 : size / 5 + 1;
        req.setAttribute("pages", pages);

        List<Route> routes = (List<Route>) routeService.findRecords(id);
        req.setAttribute("routes", routes);
        req.getRequestDispatcher("/view/routes/list.jsp").forward(req, resp);
    }

}
