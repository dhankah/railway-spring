package com.mospan.railwayspring.dao;

import com.mospan.railwayspring.model.db.*;
import com.mospan.railwayspring.service.StationService;
import com.mospan.railwayspring.service.TripService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public class RouteDao implements Dao<Route> {

    Configuration con = new Configuration().configure().addAnnotatedClass(Route.class).addAnnotatedClass(Station.class)
            .addAnnotatedClass(Trip.class).addAnnotatedClass(Ticket.class).addAnnotatedClass(User.class)
            .addAnnotatedClass(Detail.class);

    SessionFactory sf = con.buildSessionFactory();

    Session session;

    @Override
    public void insert(Route route) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(route);
        tx.commit();
        session.close();
        route.setId(findByParams(route.getStartStation(), route.getEndStation(), route.getDepartTime(), route.getTime()));
        createTripsForRoute(route);
    }

    @Override
    public void update(Route route) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.update(route);
        tx.commit();
        session.close();
    }

    @Override
    public Route find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Route findById(long id) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Route route = session.find(Route.class, id);
        tx.commit();
        session.close();
        return route;
    }

    @Override
    public void delete(Route route) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(route);
        tx.commit();
        session.close();
    }

    @Override
    public Collection<Route> findAll() {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Route");
        List<Route> routes = query.list();
        tx.commit();
        session.close();
        return routes;
    }

    public Collection<Route> findByStations(String startStation, String endStation) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Route where startStation = :ss and endStation = :es");
        query.setParameter("ss", new StationService().find(startStation));
        query.setParameter("es", new StationService().find(endStation));
        List<Route> routes = query.list();
        tx.commit();
        session.close();
        return routes;
    }

    public Collection<Route> findRecords(long id) {


        if (id != 1) {
            id = id - 1;
            id = id * 10 + 1;
        }
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Route");
        query.setFirstResult((int) (id - 1));
        query.setMaxResults(10);
        List<Route> routes = query.list();
        tx.commit();
        session.close();
        return routes;

    }


    public Collection<Route> findByStation(Station station) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Route where startStation = :ss  or endStation = :ss");
        query.setParameter("ss", station);
        List<Route> routes = query.list();
        tx.commit();
        session.close();
        return routes;

    }

    public void createTripsForRoute(Route route) {
        for (int i = 0; i < 35; i++) {
            Trip trip = new Trip();
            trip.setRoute(route);
            trip.setAvailablePlaces(36);
            trip.setDepartDate(LocalDate.now().plusDays(i));
            LocalDateTime dt = trip.getDepartDate().atTime(route.getDepartTime());
            trip.setArrivalDate((dt.plusSeconds(route.getTime())).toLocalDate());
            new TripService().insert(trip);
        }
    }

    public long findByParams(Station s1, Station s2, LocalTime depTime, long time) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select id from Route where startStation = :ss  and endStation = :es " +
                "and departTime = :dt and time = :t");
        query.setParameter("ss", s1);
        query.setParameter("es", s2);
        query.setParameter("dt", depTime);
        query.setParameter("t", time);

        long id = (long) query.uniqueResult();
        tx.commit();
        session.close();
        return id;
    }
}
