package likelion.tobyspring31.dao.connection;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    Connection getConnection() throws SQLException;
}
