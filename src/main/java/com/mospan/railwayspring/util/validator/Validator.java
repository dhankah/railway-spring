package com.mospan.railwayspring.util.validator;


import com.mospan.railwayspring.dao.ConnectionPool;
import com.mospan.railwayspring.model.db.Station;
import com.mospan.railwayspring.model.db.User;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validator {

    private static final Logger logger = Logger.getLogger(Validator.class);
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;

    public boolean validateStations(Station station) {

    /*    session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Station where name = :n");
        query.setParameter(1, station.getName());
        Station st = (Station) query.uniqueResult();
        tx.commit();
        session.close();*/

        logger.info("Starting station form validation");
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT * FROM station WHERE name = ?");
            st.setString(1, station.getName());
            ResultSet rs = st.executeQuery();
            rs.next();
            String temp = rs.getString("name");
        } catch (SQLException e) {
            logger.info("Station form validation successfully passed");
            return true;
        } finally {
            cp.closeConnection(con);
        }
        logger.info("Station form validation failed");
        return false;
    }

    public String validateUser(User user, User userUpd) {
        logger.info("Starting user edit form validation");
        con = cp.getConnection();
        PreparedStatement st = null;
        if (user.getLogin().equals(userUpd.getLogin()) && user.getDetails().getEmail().equals(userUpd.getDetails().getEmail())
        && user.getDetails().getFirstName().equals(userUpd.getDetails().getFirstName()) &&
                user.getDetails().getLastName().equals(userUpd.getDetails().getLastName())) {
            logger.info("No changes were made in user edit form");
            return "no_change";
        }
        try {
            ResultSet rs;
            if (!user.getLogin().equals(userUpd.getLogin())) {
                st = con.prepareStatement("SELECT * FROM user WHERE login = ?");
                st.setString(1, userUpd.getLogin());
                rs = st.executeQuery();
                rs.next();
                String temp = rs.getString("login");
                logger.info("User edit form validation failed: login is taken");
                return "false";
            } else if (!user.getDetails().getEmail().equals(userUpd.getDetails().getEmail())) {
                st = con.prepareStatement("SELECT * FROM detail WHERE email = ?");
                st.setString(1, userUpd.getDetails().getEmail());
                rs = st.executeQuery();
                rs.next();
                String temp = rs.getString("email");
                logger.info("User edit form validation failed: email is taken");
                return "false";
            }

        } catch (SQLException e) {
            //validation was successful, there is no user with such credentials
            logger.info("User edit form validation successfully passed");
            return "true";
        } finally {
            cp.closeConnection(con);
        }
        //validation was not successful, there are users with such credentials
        return "true";
    }


    public boolean validateRegisterUser(User user) {
        logger.info("Starting user register form validation");
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            ResultSet rs;
            st = con.prepareStatement("SELECT * FROM user WHERE login = ?");
            st.setString(1, user.getLogin());
            rs = st.executeQuery();
            rs.next();
            String temp = rs.getString("login");
        } catch (SQLException e) {
            logger.info("Login validation passed, going to email validation");
            return validateEmailRegister(user);
        } finally {
            cp.closeConnection(con);
        }
        //validation was not successful, there are users with such credentials
        logger.info("User register form validation failed on checking login");
        return false;
    }

    public boolean validateEmailRegister(User user) {
        logger.info("Starting user register form validation");
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT * FROM detail WHERE email = ?");
            st.setString(1, user.getDetails().getEmail());
            ResultSet rs = st.executeQuery();
            rs.next();
            String temp = rs.getString("email");
        } catch (SQLException e) {
            //validation was successful, there is no user with such credentials
            logger.info("User register form validation successfully passed");
            return true;
        } finally {
            cp.closeConnection(con);
        }
        //validation was not successful, there are users with such credentials
        logger.info("User register form validation failed on checking email");
        return false;
    }

}