package com.mospan.railwayspring.dao.interfaces;

import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.model.db.Trip;

import java.time.LocalDate;
import java.util.Collection;

public interface TripDao {
    void insert(Trip trip);
    void update(Trip trip);
    Trip findById(long id);
    void delete(Trip trip);
    Collection<Trip> findAll();
    Collection<Trip> findTripsForRoute(Route route);
    Collection<Trip> findRecords(Route route, LocalDate date);
}
