package likelion.tobyspring31.dao;


import likelion.tobyspring31.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) {
        String sql = "INSERT INTO users(id, name, password) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM users");
    }

    public User findById(String id) {
        String sql = "SELECT * FROM users WHERE id=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"));
            return user;
        }
    };

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users;", Integer.class);
    }
}
