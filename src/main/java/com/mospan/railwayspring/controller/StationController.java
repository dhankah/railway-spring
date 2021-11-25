package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.db.Station;

import com.mospan.railwayspring.service.StationService;
import com.mospan.railwayspring.util.validator.Validator;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class StationController {


    private static final Logger logger = Logger.getLogger(StationController.class);
    static Validator validator = new Validator();

    /**
     * PUT /stations/{id}
     * Updates specified station
     */
    @PostMapping("/stations/{id}")
    public RedirectView update(@PathVariable long id, HttpServletRequest req) throws ServletException, IOException {
        logger.info("updating station " + id);
        req.setCharacterEncoding("UTF-8");
        Station station = new StationService().findById(id);
        station.setName(req.getParameter("name"));

        if (validator.validateStations(station)) {
            logger.info("updated station " + station.getId() + " successfully");
            new StationService().update(station);
            return new RedirectView(req.getContextPath() + "/stations/1/page");
        }

        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));
        logger.info("updating station " + station.getId() + " failed");

        req.getSession().setAttribute("errorMessage", rb.getString("station_exists"));

        return new RedirectView(req.getContextPath() + "/stations/1/page");
    }
    /**
     * GET /stations/{id}/edit
     * Displays edit form for given station
     */

    @GetMapping("/stations/{id}/edit")
    public String edit(@PathVariable long id, HttpServletRequest req) throws ServletException, IOException {
        logger.info("forwarding to station edit page");
        Station station = new StationService().findById(id);
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("station", station);
        return "forward:/view/stations/edit.jsp";
    }

    /**
     * POST /stations
     * Save new stations
     */
    @PostMapping("/stations")
    public RedirectView store(HttpServletRequest req) {

        logger.info("saving station");
        Station station = new Station();
        station.setName(req.getParameter("name"));
        if (validator.validateStations(station)) {
            logger.info("station " + station.getName() + " saved successfully");
            new StationService().insert(station);
            return new RedirectView(req.getContextPath() + "/stations/1/page");
        }
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

        logger.info("saving station " + station.getName() + " failed");

        req.getSession().setAttribute("errorMessage", rb.getString("station_exists"));

        return new RedirectView(req.getContextPath() + "/stations/1/page");
    }

    /**
     * GET /stations
     * Displays list of stations
     */
    @GetMapping("/stations")
    public void list(HttpServletRequest req) {
        goToPage(1, req);
    }

    /**
     * DELETE stations/{id}
     * Removes a specified station from db
     */
    @PostMapping(value = "stations/{id}", params = "_method=delete")
    public RedirectView delete(@PathVariable long id, HttpServletRequest req) throws ServletException, IOException {
        Station station = new StationService().findById(id);
        logger.info("deleting station " + station.getName());
        new StationService().delete(station);
        return new RedirectView(req.getContextPath() + "/stations/1/page");
    }

    /**
     * GET /stations/{id}/page
     * Displays list of stations for the page {id}
     */
    @GetMapping("/stations/{id}/page")
    public String goToPage(@PathVariable long id, HttpServletRequest req) {
        logger.info("forwarding to page " + id + " of stations");
        List<Station> stationsForList = (List<Station>) new StationService().findAll();
        req.setAttribute("stations", stationsForList);

        int size = new StationService().findAll().size();
        int pages = size % 10 == 0 ? size / 10 : size / 10 + 1;
        req.setAttribute("pages", pages);

        List<Station> stations = (List<Station>) new StationService().findRecords(id);
        req.setAttribute("stations", stations);
        return "forward:/view/stations/list.jsp";
    }
}