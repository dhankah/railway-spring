package com.mospan.railwayspring.dao;

import com.mospan.railwayspring.model.db.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TicketDao implements Dao<Ticket>{
    Configuration con = new Configuration().configure().addAnnotatedClass(Trip.class)
            .addAnnotatedClass(Route.class).addAnnotatedClass(Station.class)
            .addAnnotatedClass(Ticket.class).addAnnotatedClass(User.class).addAnnotatedClass(Detail.class);

    SessionFactory sf = con.buildSessionFactory();

    Session session;

    @Override
    public void insert(Ticket ticket) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(ticket);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Ticket ticket) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.update(ticket);
        tx.commit();
        session.close();
    }

    @Override
    public Ticket find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ticket findById(long id) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Ticket ticket = session.find(Ticket.class, id);
        tx.commit();
        session.close();
        return ticket;
    }

    @Override
    public void delete(Ticket ticket) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(ticket);
        tx.commit();
        session.close();
    }

    @Override
    public Collection<Ticket> findAll() {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Ticket");
        List<Ticket> tickets = query.list();
        tx.commit();
        session.close();
        return tickets;

    }

    public Collection<Integer> findSeats(Trip trip) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select seat from Ticket where trip = :t");
        query.setParameter("t", trip);
        List<Integer> seats = query.list();
        tx.commit();
        session.close();
        return seats;
    }




    public List<List<Ticket>> findAllForUser(User user) {

        List<List<Ticket>> tickets = new ArrayList<>();
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Ticket where user = :t");
        query.setParameter("t", user);
        List<Ticket> temp = query.list();

        tx.commit();
        session.close();
        List<Ticket> oldTickets = new ArrayList<>();
        List<Ticket> upcomingTickets = new ArrayList<>();

        for (Ticket ticket : temp) {
            if (ticket.getTrip().getDepartDate().isBefore(LocalDate.now())) {

                oldTickets.add(ticket);
            } else if (ticket.getTrip().getDepartDate().isAfter(LocalDate.now())) {

                upcomingTickets.add(ticket);
            } else if (ticket.getTrip().getRoute().getDepartTime().isBefore(LocalTime.now())) {

                oldTickets.add(ticket);
            } else {
                upcomingTickets.add(ticket);
            }
        }
        tickets.add(oldTickets);
        tickets.add(upcomingTickets);

        return tickets;
    }

    public Collection<Ticket> findTicketsForTrip(Trip trip) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Ticket where trip = :t");
        query.setParameter("t", trip);
        List<Ticket> tickets = query.list();

        return tickets;
    }
}
