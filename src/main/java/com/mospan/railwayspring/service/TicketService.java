package com.mospan.railwayspring.service;


import com.mospan.railwayspring.dao.implementation.TicketDaoImpl;
import com.mospan.railwayspring.dao.interfaces.TicketDao;
import com.mospan.railwayspring.model.db.Ticket;
import com.mospan.railwayspring.model.db.Trip;
import com.mospan.railwayspring.util.EmailSender;
import com.mospan.railwayspring.model.db.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TicketService {
    TicketDao dao = new TicketDaoImpl();

    public void insert(Ticket ticket) {
        dao.insert(ticket);
    }
    public void update(Ticket ticket) {
        dao.update(ticket);
    }
    public Ticket findById(long id) {
        return dao.findById(id);
    }
    public void delete(Ticket ticket, boolean needNotif) {
        if (needNotif) {
            EmailSender.sendTicketNotification(ticket, "ticket_return");
        }
        dao.delete(ticket);
    }
    public Collection<Ticket> findAll() {
        return dao.findAll();
    }
    public Collection<Integer> findSeats(Trip trip) {
        return dao.findSeats(trip);
    }
    public List<List<Ticket>> findAllForUser(User user) {
        return dao.findAllForUser(user);
    }
    public Collection<Ticket> findTicketsForTrip(Trip trip) {
        return dao.findTicketsForTrip(trip);
    }

}
