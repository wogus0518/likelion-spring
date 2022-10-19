package likelion.tobyspring31.dao;

import likelion.tobyspring31.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    AnnotationConfigApplicationContext ac = new
            AnnotationConfigApplicationContext(UserDaoFactory.class);

    @Test
    void addAndSelect() throws SQLException {
        UserDao userDao = ac.getBean("localUserDao", UserDao.class);
        String id = "23";
        userDao.add(new User(id, "jae", "123123"));

        User findUser = userDao.findById(id);
        assertEquals("jae", findUser.getName());
    }

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name=" + beanDefinitionName + " object=" +
                    bean);
        }
    }

}