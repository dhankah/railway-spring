package com.mospan.railwayspring.dao;

import com.mospan.railwayspring.model.Detail;
import com.mospan.railwayspring.model.Role;
import com.mospan.railwayspring.model.User;
import com.mospan.railwayspring.service.DetailService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserDao implements Dao<User>{

    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;
    DetailService detailService = new DetailService();

    @Override
    public void insert(User user) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO user (login, password, role_id, detail_id)" +
                    " VALUES (?, ?, ?, ?)");

            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());

            Detail detail = user.getDetails();
            detailService.insert(detail);


            st.setLong(4, detailService.find(detail.getEmail()).getId());

            if (user.getRole().equals(Role.ADMIN)) {
                st.setLong(3, 0);
            }
            else if (user.getRole().equals(Role.CLIENT)) {
                st.setLong(3, 1);
            }

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

    }

    @Override
    public void delete(User user) {
        con = cp.getConnection();
        new DetailService().delete(user.getDetails());
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE id = ?");
            st.setLong(1, user.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public void update(User user) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE user SET login = ?, password = ?, balance = ? WHERE id = ?");

            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setDouble(3, user.getBalance());
            st.setLong(4, user.getId());

            st.executeUpdate();

            Detail detail = user.getDetails();
            detailService.update(detail);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public User find(String login) {
        User user = new User();

        con = cp.getConnection();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM user WHERE login = ?");

            st.setString(1, login);

            ResultSet rs = st.executeQuery();
            rs.next();

            user.setLogin(login);
            user.setPassword(rs.getString("password"));
            user.setId(rs.getLong("id"));
            user.setBalance(rs.getDouble("balance"));
            user.setDetails(detailService.findById(rs.getLong("detail_id")));


            if (rs.getInt("role_id") == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.CLIENT);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            cp.closeConnection(con);
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = new User();

        con = cp.getConnection();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM user WHERE detail_id = ?");

            st.setLong(1, new DetailService().find(email).getId());

            ResultSet rs = st.executeQuery();
            rs.next();

            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setId(rs.getLong("id"));
            user.setBalance(rs.getDouble("balance"));
            user.setDetails(detailService.findById(rs.getLong("detail_id")));


            if (rs.getInt("role_id") == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.CLIENT);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            cp.closeConnection(con);
        }
        return user;
    }


    @Override
    public User findById(long id) {
        con = cp.getConnection();
        User user = new User();
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM user WHERE id = ?");
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();

            user.setId(id);
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setBalance(rs.getDouble("balance"));
            if (rs.getInt("role_id") == 0) {
                user.setRole(Role.ADMIN);
            }
            else if (rs.getInt("role_id") == 1) {
                user.setRole(Role.CLIENT);
            }


            user.setDetails(detailService.findById(rs.getLong("detail_id")));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return user;
    }

    @Override
    public Collection<User> findAll() {
       throw new UnsupportedOperationException();
    }

    public String getEmailSenderData() {
        con = cp.getConnection();
        String p = null;
        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT password FROM email_data where email = 'railway.service@outlook.com'");

            ResultSet rs = st.executeQuery();
            rs.next();
            p = rs.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return p;
    }
}
