package com.mospan.railwayspring.service;

import com.mospan.railwayspring.dao.implementation.RouteDaoImpl;

import com.mospan.railwayspring.dao.interfaces.RouteDao;
import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.model.db.Station;
import com.mospan.railwayspring.model.db.Trip;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RouteService {
    RouteDao dao = new RouteDaoImpl();

    public void insert(Route route) {
        dao.insert(route);
    }
    public void update(Route route) {
        dao.update(route);
    }
    public Route findById(long id) {
        return dao.findById(id);
    }
    public void delete(Route route) {

        Collection<Trip> trips = new TripService().findTripsForRoute(route);
        if (null != trips) {
        for (Trip trip : trips) {
            new TripService().delete(trip);
        }
        }
        dao.delete(route);
    }
    public Collection<Route> findAll() {
        return dao.findAll();
    }
    public Collection<Route> findByStations(String startStation, String endStation){
        return dao.findByStations(startStation, endStation);
    }
    public Collection<Route> findRecords(long id) {
        return dao.findRecords(id);
    }
    public Collection<Route> findByStation(Station station) {
        return dao.findByStation(station);
    }
}
