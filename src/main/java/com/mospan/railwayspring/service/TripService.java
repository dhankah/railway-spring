package com.mospan.railwayspring.service;

import com.mospan.railway.util.EmailSender;
import com.mospan.railwayspring.dao.TripDao;
import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.model.db.Ticket;
import com.mospan.railwayspring.model.db.Trip;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class TripService {

    TripDao dao = new TripDao();

    public void insert(Trip trip) {
        dao.insert(trip);
    }
    public void update(Trip trip) {
        dao.update(trip);
    }
    public Trip find(String param) {
        return dao.find(param);
    }
    public Trip findById(long id) {
        return dao.findById(id);
    }
    public void delete(Trip trip) {
        Collection<Ticket> tickets = new TicketService().findTicketsForTrip(trip);
        for (Ticket ticket : tickets) {
            if (ticket.getTrip().getDepartDate().isAfter(LocalDate.now()) ||
                    ticket.getTrip().getDepartDate().isEqual(LocalDate.now()) && ticket.getTrip().getRoute().getDepartTime().isAfter(LocalTime.now())) {
                EmailSender.sendTicketNotification(ticket, "trip_cancel");
            }
            new TicketService().delete(ticket, false);
        }
        dao.delete(trip);
    }
    public Collection<Trip> findAll() {
        return dao.findAll();
    }
    public Trip getTripForDate(long routeId, LocalDate date) {
        return dao.getTripForDate(routeId, date);
    }
    public Collection<Trip> findTrips(Route route, LocalDate date) {
        return dao.findTrips(route, date);
    }
    public Collection<Trip> findRecords(Route route, LocalDate date) {
        return dao.findRecords(route, date);
    }
    public Collection<Trip> findTripsForRoute(Route route){
        return dao.findTripsForRoute(route);
    }
}
