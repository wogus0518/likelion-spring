package likelion.tobyspring31.dao;

import likelion.tobyspring31.dao.strategy.StatementStrategy;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy strategy) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = strategy.makePreparedStatement(conn); //전달받은 전략을 사용한다.
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        close(conn, pstmt, null);
    }

//    public void executeSql(String sql) throws SQLException{
//        workWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
//                return conn.prepareStatement(sql);
//            }
//        });
//    }

    public void executeSql(String sql) throws SQLException{
        workWithStatementStrategy(conn -> conn.prepareStatement(sql));
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
