package likelion.tobyspring31.dao.connection;

import likelion.tobyspring31.dao.connection.ConnectionMaker;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@Component
public class LocalConnectionMaker implements ConnectionMaker {
    @Override
    public Connection getConnection() throws SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");
        return DriverManager.getConnection(dbHost, dbUser, dbPassword);
    }
}
