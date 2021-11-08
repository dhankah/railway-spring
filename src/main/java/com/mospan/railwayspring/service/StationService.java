package com.mospan.railwayspring.service;

import com.mospan.railwayspring.dao.StationDao;
import com.mospan.railwayspring.model.Route;
import com.mospan.railwayspring.model.Station;

import java.util.Collection;

public class StationService {
    StationDao dao = new StationDao();

    public void insert(Station station) {
        dao.insert(station);
    }
    public void update(Station station) {
        dao.update(station);
    }
    public Station find(String name) {
        return dao.find(name);
    }
    public Station findById(long id) {
        return dao.findById(id);
    }
    public void delete(Station station) {
        Collection<Route> routes = new RouteService().findByStation(station);
        if (routes != null) {
            for (Route route : routes) {
                new RouteService().delete(route);
            }
        }
        dao.delete(station);
    }
    public Collection<Station> findAll() {
        return dao.findAll();
    }
    public Collection<Station> findRecords(long id) {
        return dao.findRecords(id);
    }

}