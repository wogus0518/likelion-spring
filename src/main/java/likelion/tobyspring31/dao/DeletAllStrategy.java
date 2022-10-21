package likelion.tobyspring31.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletAllStrategy implements StatementStrategy{

    private String sql = "DELETE FROM users";

    @Override
    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
        return conn.prepareStatement(sql);
    }
}
