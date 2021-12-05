package com.mospan.railwayspring.dao.interfaces;

import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.model.db.Station;

import java.time.LocalTime;
import java.util.Collection;

public interface RouteDao {
    void insert(Route route);
    void update(Route route);
    Route findById(long id);
    void delete(Route route);
    Collection<Route> findAll();
    Collection<Route> findByStations(String startStation, String endStation);
    Collection<Route> findRecords(long id);
    Collection<Route> findByStation(Station station);
    void createTripsForRoute(Route route);
    long findByParams(Station s1, Station s2, LocalTime depTime, long time);
}
