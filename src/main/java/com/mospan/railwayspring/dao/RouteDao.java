package com.mospan.railwayspring.dao;

import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.model.db.Station;
import com.mospan.railwayspring.model.db.Trip;
import com.mospan.railwayspring.service.StationService;
import com.mospan.railwayspring.service.TripService;
import org.apache.log4j.Logger;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RouteDao implements Dao<Route> {
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;
    private static final Logger logger = Logger.getLogger(RouteDao.class);
    StationService stationService = new StationService();

    @Override
    public void insert(Route route) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO route (start_station_id, end_station_id, depart_time, time, price)" +
                    " VALUES (?, ?, ?, ?, ?)");

            st.setLong(1, route.getStartStation().getId());
            st.setLong(2, route.getEndStation().getId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setLong(4, route.getTime());
            st.setDouble(5, route.getPrice());


            st.executeUpdate();

            route.setId(findByParams(route.getStartStation(), route.getEndStation(), route.getDepartTime(), route.getTime()));
            System.out.println("here i looked and what ive found: " + route.getId());
            createTripsForRoute(route);
        } catch (SQLException e) {
            logger.info("insert failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

    }

    @Override
    public void update(Route route) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE route SET start_station_id = ?, end_station_id = ?, depart_time = ?, " +
                    "time = ?, price = ? WHERE id = ?");

            st.setLong(1, route.getStartStation().getId());
            st.setLong(2, route.getEndStation().getId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setLong(4, route.getTime());
            st.setDouble(5, route.getPrice());
            st.setLong(6, route.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            logger.info("update failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Route find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Route findById(long id) {
        con = cp.getConnection();
        Route route = new Route();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE id = ?");

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();


            route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
            route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
            route.setTime(rs.getLong("time"));
            route.setDepartTime(rs.getTime("depart_time").toLocalTime());
            route.setPrice(rs.getDouble("price"));


            route.setId(id);
        } catch (SQLException e) {
            logger.info("search by id failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return route;
    }

    @Override
    public void delete(Route route) {

        con = cp.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM route WHERE id = ?");
            st.setLong(1, route.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.info("deletion failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Collection<Route> findAll() {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Route route = new Route();

                route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
                route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
                route.setTime(rs.getLong("time"));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setPrice(rs.getDouble("price"));
                route.setId(rs.getLong("id"));

                routes.add(route);

            }

        } catch (SQLException e) {
            logger.info("search for all failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return routes;
    }

    public Collection<Route> findByStations(String startStation, String endStation) {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE start_station_id = ? AND end_station_id = ?");

            st.setLong(1, stationService.find(startStation).getId());
            st.setLong(2, stationService.find(endStation).getId());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Route route = new Route();
                route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
                route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
                route.setTime(rs.getLong("time"));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setId(rs.getLong("id"));
                route.setPrice(rs.getDouble("price"));
                routes.add(route);
            }
            if (routes.isEmpty()) {
                return null;
            }
        } catch (SQLException e) {
            logger.info("search by stations failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return routes;
    }

    public Collection<Route> findRecords(long id) {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        if (id != 1) {
            id = id - 1;
            id = id * 10 + 1;
        }


        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route LIMIT ?, 10");
            st.setLong(1, id - 1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Route route = new Route();
                route.setId(rs.getLong("id"));
                route.setStartStation(new StationService().findById(rs.getLong("start_station_id")));
                route.setEndStation(new StationService().findById(rs.getLong("end_station_id")));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setTime(rs.getLong("time"));
                route.setPrice(rs.getDouble("price"));
                routes.add(route);
            }

        } catch (SQLException e) {
            logger.info("opening page failed");
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return routes;
    }

    public Collection<Route> findByStation(Station station) {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE start_station_id = ? UNION SELECT * FROM route WHERE end_station_id = ?");

            st.setLong(1, station.getId());
            st.setLong(2, station.getId());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Route route = new Route();
                route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
                route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
                route.setTime(rs.getLong("time"));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setId(rs.getLong("id"));
                route.setPrice(rs.getDouble("price"));
                routes.add(route);
            }
            if (routes.isEmpty()) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
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
        con = cp.getConnection();
        long id = 0;
        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT id FROM route WHERE start_station_id = ? AND end_station_id = ? AND " +
                    "depart_time = ? AND time = ?");

            st.setLong(1, s1.getId());
            st.setLong(2, s2.getId());
            st.setTime(3, Time.valueOf(depTime));
            st.setLong(4, time);

            ResultSet rs = st.executeQuery();

            rs.next();
            id = rs.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return id;
    }
}
