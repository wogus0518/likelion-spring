package likelion.tobyspring31.dao;

import likelion.tobyspring31.dao.connection.LocalConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao localUserDao() {
        return new UserDao(new LocalConnectionMaker());
    }
}
