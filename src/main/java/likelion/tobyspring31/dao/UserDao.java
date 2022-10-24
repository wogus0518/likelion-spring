package likelion.tobyspring31.dao;


import likelion.tobyspring31.dao.connection.ConnectionMaker;
import likelion.tobyspring31.dao.strategy.AddStrategy;
import likelion.tobyspring31.dao.strategy.DeletAllStrategy;
import likelion.tobyspring31.dao.strategy.StatementStrategy;
import likelion.tobyspring31.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class UserDao {

    private final ConnectionMaker cm;

    @Autowired
    public UserDao(ConnectionMaker cm) {
        this.cm = cm;
    }

    public void add(User user) throws SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            private String sql = "INSERT INTO users(id, name, password) VALUES(?, ?, ?)";

            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());
                return pstmt;
            }
        });
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

    private void jdbcContextWithStatementStrategy(StatementStrategy strategy) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = cm.getConnection();
            pstmt = strategy.makePreparedStatement(conn); //전달받은 전략을 사용한다.
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        close(conn, pstmt, null);
    }

    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            private String sql = "DELETE FROM users";

            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                return conn.prepareStatement(sql);
            }
        });
    }

    public int getCount() throws SQLException {
        List<User> users = findAll();
        return users.size();
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
