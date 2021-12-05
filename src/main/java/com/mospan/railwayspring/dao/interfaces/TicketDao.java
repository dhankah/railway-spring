package com.mospan.railwayspring.dao.interfaces;

import com.mospan.railwayspring.model.db.Ticket;
import com.mospan.railwayspring.model.db.Trip;
import com.mospan.railwayspring.model.db.User;

import java.util.Collection;
import java.util.List;

public interface TicketDao {
    void insert(Ticket ticket);
    void update(Ticket ticket);
    Ticket findById(long id);
    void delete(Ticket ticket);
    Collection<Ticket> findAll();
    Collection<Integer> findSeats(Trip trip);
    List<List<Ticket>> findAllForUser(User user);
    Collection<Ticket> findTicketsForTrip(Trip trip);

}
