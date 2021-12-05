package com.mospan.railwayspring.dao.implementation;


import com.mospan.railwayspring.dao.interfaces.TripDao;
import com.mospan.railwayspring.model.db.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class TripDaoImpl implements TripDao {
    Configuration con = new Configuration().configure().addAnnotatedClass(Trip.class).addAnnotatedClass(Route.class)
            .addAnnotatedClass(Station.class).addAnnotatedClass(Ticket.class).addAnnotatedClass(User.class).addAnnotatedClass(Detail.class);

    SessionFactory sf = con.buildSessionFactory();

    Session session;

    @Override
    public void insert(Trip trip) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(trip);
        tx.commit();
        session.close();

    }

    @Override
    public void update(Trip trip) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.update(trip);
        tx.commit();
        session.close();
    }

    @Override
    public Trip findById(long id) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Trip trip = session.find(Trip.class, id);
        tx.commit();
        session.close();
        return trip;
    }

    @Override
    public void delete(Trip trip) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(trip);
        tx.commit();
        session.close();
    }

    @Override
    public Collection<Trip> findAll() {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Trip");
        List<Trip> trips = query.list();
        tx.commit();
        session.close();
        return trips;
    }


    @Override
    public Collection<Trip> findTripsForRoute(Route route){
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Trip where route = :r");
        query.setParameter("r", route);
        List<Trip> trips = query.list();
        tx.commit();
        session.close();
        return trips;

    }

    @Override
    public Collection<Trip> findRecords(Route route, LocalDate date) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Trip where route = :r and departDate = :dt");
        query.setParameter("r", route);
        query.setParameter("dt", date);
        List<Trip> trips = query.list();
        tx.commit();
        System.out.println(trips.size());
        session.close();
        return trips;
    }
}
