package com.mospan.railwayspring.service;


import com.mospan.railwayspring.dao.TicketDao;
import com.mospan.railwayspring.model.Ticket;
import com.mospan.railwayspring.model.Trip;
import com.mospan.railway.util.EmailSender;

import java.util.Collection;
import java.util.List;

public class TicketService {
    TicketDao dao = new TicketDao();

    public void insert(Ticket ticket) {
        dao.insert(ticket);
    }
    public void update(Ticket ticket) {
        dao.update(ticket);
    }
    public Ticket find(String param) {
        return dao.find(param);
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
    public List<List<Ticket>> findAllForUser(long id) {
        return dao.findAllForUser(id);
    }
    public Collection<Ticket> findTicketsForTrip(Trip trip) {
        return dao.findTicketsForTrip(trip);
    }

}
