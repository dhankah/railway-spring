package com.mospan.railwayspring.dao;



import com.mospan.railwayspring.model.db.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDao implements Dao<User>{

    Configuration con = new Configuration().configure().addAnnotatedClass(User.class).addAnnotatedClass(Detail.class)
            .addAnnotatedClass(Ticket.class).addAnnotatedClass(Trip.class).addAnnotatedClass(Route.class)
            .addAnnotatedClass(Station.class);

    SessionFactory sf = con.buildSessionFactory();

    Session session;


    @Override
    public void insert(User user) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        session = sf.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User find(String login) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query<User> query = session.createQuery("from User where login = :n");
        query.setParameter("n", login);

        User user = query.uniqueResult();

        tx.commit();
        session.close();

        return user;
    }

    public User findByEmail(String email) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from User u where u.details.email = :n");
        query.setParameter("n", email);
        User user = (User) query.uniqueResult();
        tx.commit();
        session.close();
        return user;
    }


    @Override
    public User findById(long id) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        User user = session.find(User.class, id);
        tx.commit();
        session.close();
        return user;
    }

    @Override
    public Collection<User> findAll() {
       throw new UnsupportedOperationException();
    }

    public String getEmailSenderData() {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SELECT password FROM email_data where email = 'railway.service@outlook.com'");
        String p = (String) query.uniqueResult();
        tx.commit();
        session.close();

        return p;
    }
}
