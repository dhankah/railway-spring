package com.mospan.railwayspring.util.validator;


import com.mospan.railwayspring.model.db.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class Validator {

    private static final Logger logger = Logger.getLogger(Validator.class);
    Configuration con = new Configuration().configure().addAnnotatedClass(Trip.class)
            .addAnnotatedClass(Route.class).addAnnotatedClass(Station.class)
            .addAnnotatedClass(Ticket.class).addAnnotatedClass(User.class).addAnnotatedClass(Detail.class);

    SessionFactory sf = con.buildSessionFactory();

    Session session;

    public boolean validateStations(Station station) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Station where name = :n");
        query.setParameter("n", station.getName());
        Station st = (Station) query.uniqueResult();
        tx.commit();
        session.close();

        if (null != st) {
            logger.info("Station form validation failed");
            return false;
        }
        logger.info("Station form validation successfully passed");
        return true;
    }

    public String validateUser(User user, User userUpd) {


        logger.info("Starting user edit form validation");

        if (user.getLogin().equals(userUpd.getLogin()) && user.getDetails().getEmail().equals(userUpd.getDetails().getEmail())
        && user.getDetails().getFirstName().equals(userUpd.getDetails().getFirstName()) &&
                user.getDetails().getLastName().equals(userUpd.getDetails().getLastName())) {
            logger.info("No changes were made in user edit form");
            return "no_change";
        }


        if (!user.getLogin().equals(userUpd.getLogin())) {
            session = sf.openSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from User where login = :l");
            query.setParameter("l", userUpd.getLogin());
            User u = (User) query.uniqueResult();
            tx.commit();
            session.close();
            if (null != u) {
                logger.info("User edit form validation failed: login is taken");
                return "false";
            }
        }   if (!user.getDetails().getEmail().equals(userUpd.getDetails().getEmail())) {
                session = sf.openSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("from Detail where email = :e");
                query.setParameter("e", userUpd.getDetails().getEmail());
                Detail d = (Detail) query.uniqueResult();
                tx.commit();
                session.close();
                if (null != d) {
                    logger.info("User edit form validation failed: email is taken");
                    return "false";
                }
            }
        logger.info("User edit form validation successfully passed");
        return "true";
    }



    public boolean validateRegisterUser(User user) {
        logger.info("Starting user register form validation");

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from User where login = :l");
        query.setParameter("l", user.getLogin());
        User u = (User) query.uniqueResult();
        tx.commit();
        session.close();
        if (null != u) {
            logger.info("User register form validation failed on checking login");
            return false;
        }
        logger.info("Login validation passed, going to email validation");
        return validateEmailRegister(user);
    }

    public boolean validateEmailRegister(User user) {
        logger.info("Starting user register form validation");

        session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Detail where email = :e");
        query.setParameter("e", user.getDetails().getEmail());
        Detail d = (Detail) query.uniqueResult();
        tx.commit();
        session.close();
        if (null != d) {
            logger.info("User register form validation failed on checking email");
            return false;
        }
        logger.info("User register form validation successfully passed");
        return true;

    }

}