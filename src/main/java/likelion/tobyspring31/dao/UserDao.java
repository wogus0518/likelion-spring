package likelion.tobyspring31.dao;


import com.line.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class UserDao {
    private final ConnectionMaker cm;

    public UserDao(ConnectionMaker cm) {
        this.cm = cm;
    }

    public void add(User user) throws SQLException {
        String sql = "INSERT INTO users(id, name, password) VALUES(?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = cm.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(conn, ps, null);
        }
    }

    public User findById(String id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = cm.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();


            if (rs.next()) {
                return new User(rs.getString(1), rs.getString(2), rs.getString(3));
            } else {
                throw new NoSuchElementException("not found id=" + id);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            close(conn, ps, rs);
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = cm.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            return users;
        } catch (SQLException e) {
            throw e;
        } finally {
            close(conn, ps, rs);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
