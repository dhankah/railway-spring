package com.mospan.railwayspring.dao.implementation;

import com.mospan.railwayspring.dao.interfaces.DetailDao;
import com.mospan.railwayspring.model.db.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class DetailDaoImpl implements DetailDao {

    Configuration con = new Configuration().configure().addAnnotatedClass(Detail.class);

    SessionFactory sf = con.buildSessionFactory();

    Session session;

    @Override
    public void insert(Detail detail) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(detail);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Detail detail) {
        session = sf.openSession();
        session.beginTransaction();
        session.update(detail);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Detail find(String email) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query<Detail> query = session.createQuery("from Detail where email = :n");
        query.setParameter("n", email);
        Detail detail = query.uniqueResult();
        tx.commit();
        session.close();
        return detail;
    }

    @Override
    public Detail findById(long id) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Detail detail = session.find(Detail.class, id);
        tx.commit();
        session.close();
        return detail;
    }

    @Override
    public void delete(Detail detail) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(detail);
        tx.commit();
        session.close();
    }
}
