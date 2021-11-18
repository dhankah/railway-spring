package com.mospan.railwayspring.dao;


import com.mospan.railwayspring.model.db.*;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;

public class StationDao implements Dao<Station>{

    Configuration con = new Configuration().configure().addAnnotatedClass(Station.class).addAnnotatedClass(Route.class)
            .addAnnotatedClass(Trip.class).addAnnotatedClass(Ticket.class).addAnnotatedClass(User.class).addAnnotatedClass(Detail.class);

    SessionFactory sf = con.buildSessionFactory();

    Session session;



    @Override
    public Station findById(long id) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Station station = session.find(Station.class, id);
        tx.commit();
        session.close();
        return station;
    }

    @Override
    public void insert(Station station) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(station);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Station station) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.update(station);
        tx.commit();
        session.close();
    }


    @Override
    public Station find(String name) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Station where name = :n");
        query.setParameter("n", name);
        Station station = (Station) query.uniqueResult();
        tx.commit();
        session.close();
        return station;
    }

    @Override
    public void delete(Station station) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(station);
        tx.commit();
        session.close();
    }

    @Override
    public Collection<Station> findAll() {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Station");
        List<Station> stations = query.list();
        tx.commit();
        session.close();
        return stations;
    }

    public Collection<Station> findRecords(long id) {

        if (id != 1){
          id = id - 1;
          id = id * 10 + 1;
        }

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Station");
        query.setFirstResult((int) (id - 1));
        query.setMaxResults(10);
        List<Station> stations = query.list();
        tx.commit();
        session.close();
        return stations;


    }

}