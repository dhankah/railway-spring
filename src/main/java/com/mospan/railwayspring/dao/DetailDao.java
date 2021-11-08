package com.mospan.railwayspring.dao;

import com.mospan.railwayspring.controller.TicketController;
import com.mospan.railwayspring.model.Detail;
import com.mospan.railwayspring.model.Station;
import com.mospan.railwayspring.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DetailDao implements Dao<Detail>{
    private static final Logger logger = Logger.getLogger(DetailDao.class);
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;

    @Override
    public void insert(Detail detail) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO detail (first_name, last_name, email) VALUES (?, ?, ?)");

            st.setString(1, detail.getFirstName());
            st.setString(2, detail.getLastName());
            st.setString(3, detail.getEmail());

            st.executeUpdate();
        } catch (SQLException e) {
            logger.info("insert failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public void update(Detail detail) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE detail " +
                    "SET first_name = ?, last_name = ?, email = ?" +
                    "WHERE id = ?");

            st.setString(1, detail.getFirstName());
            st.setString(2, detail.getLastName());
            st.setString(3, detail.getEmail());
            st.setLong(4, detail.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            logger.info("update failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Detail find(String email) {
        con = cp.getConnection();
        Detail detail = new Detail();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM detail WHERE email = ?");

            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            rs.next();

            detail.setFirstName(rs.getString("first_name"));
            detail.setLastName(rs.getString("last_name"));
            detail.setEmail(email);
            detail.setId(rs.getLong("id"));
        } catch (SQLException e) {
            logger.info("search by email failed");
            detail = null;
        } finally {
            cp.closeConnection(con);
        }
        return detail;

    }

    @Override
    public Detail findById(long id) {
        con = cp.getConnection();
        Detail detail = new Detail();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM detail WHERE id = ?");

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();

            detail.setFirstName(rs.getString("first_name"));
            detail.setLastName(rs.getString("last_name"));
            detail.setEmail(rs.getString("email"));
            detail.setId(id);
        } catch (SQLException e) {
            logger.info("search by id failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return detail;
    }

    @Override
    public void delete(Detail detail) {
        con = cp.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM detail WHERE id = ?");
            st.setLong(1, detail.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.info("deletion failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Collection<Detail> findAll() {
        con = cp.getConnection();
        List<Detail> details = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM detail");

            ResultSet rs = st.executeQuery();
            long id = 1;
            while (rs.next()) {
                Detail detail = findById(id);
                details.add(detail);
                id++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return details;
    }
}
