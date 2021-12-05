package com.mospan.railwayspring.service;

import com.mospan.railwayspring.model.db.User;
import com.mospan.railwayspring.util.EmailSender;
import com.mospan.railwayspring.dao.implementation.TripDaoImpl;
import com.mospan.railwayspring.dao.interfaces.TripDao;
import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.model.db.Ticket;
import com.mospan.railwayspring.model.db.Trip;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Service
public class TripService {

    TripDao dao = new TripDaoImpl();

    public void insert(Trip trip) {
        dao.insert(trip);
    }
    public void update(Trip trip) {
        dao.update(trip);
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
                User user = ticket.getUser();
                user.setBalance(user.getBalance() + ticket.getTrip().getRoute().getPrice());
                new UserService().update(user);
            }
            new TicketService().delete(ticket, false);
        }
        dao.delete(trip);
    }
    public Collection<Trip> findAll() {
        return dao.findAll();
    }

    public Collection<Trip> findRecords(Route route, LocalDate date) {
        return dao.findRecords(route, date);
    }
    public Collection<Trip> findTripsForRoute(Route route){
        return dao.findTripsForRoute(route);
    }
}
