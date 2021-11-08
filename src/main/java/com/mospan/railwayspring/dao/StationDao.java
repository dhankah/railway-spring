package com.mospan.railwayspring.dao;


import com.mospan.railwayspring.model.Station;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StationDao implements Dao<Station>{
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;

    @Override
    public Station findById(long id) {
        con = cp.getConnection();
        Station station = new Station();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station WHERE id = ?");
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();

            station.setName(rs.getString("name"));
            station.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            cp.closeConnection(con);
        }

        return station;
    }

    @Override
    public void insert(Station station) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO station (name)" +
                    " VALUES (?)");
            st.setString(1, station.getName());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public void update(Station station) {
        con = cp.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("UPDATE station SET name = ? WHERE id = ?");
            st.setString(1, station.getName());
            st.setLong(2, station.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }


    @Override
    public Station find(String name) {
        con = cp.getConnection();
        Station station = new Station();
        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station WHERE name = ?");
            st.setString(1, name);

            ResultSet rs = st.executeQuery();rs.next();
            station.setName(name);
            station.setId(rs.getLong("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return station;
    }

    @Override
    public void delete(Station station) {

        con = cp.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM station WHERE id = ?");
            st.setLong(1, station.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Collection<Station> findAll() {
        con = cp.getConnection();
        List<Station> stations = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Station station = new Station();
                station.setName(rs.getString("name"));
                station.setId(rs.getLong("id"));

                stations.add(station);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return stations;
    }

    public Collection<Station> findRecords(long id) {
        con = cp.getConnection();
        List<Station> stations = new ArrayList<>();

        if (id != 1){
          id = id - 1;
          id = id * 10 + 1;
        }


        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station LIMIT ?, 10");
            st.setLong(1, id - 1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Station station = new Station();
                station.setName(rs.getString("name"));
                station.setId(rs.getLong("id"));

                stations.add(station);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return stations;
    }

}