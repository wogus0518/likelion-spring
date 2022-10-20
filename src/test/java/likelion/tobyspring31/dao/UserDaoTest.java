package likelion.tobyspring31.dao;

import likelion.tobyspring31.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    UserDao userDao;

    @Autowired
    ApplicationContext ac;

    @BeforeEach
    @DisplayName("테스트 시작할 때 userDao 가져오기")
    void beforeEach() {
        userDao = ac.getBean("localUserDao", UserDao.class);
    }

    @AfterEach
    @DisplayName("테스트 끝나면 테이블 삭제")
    void afterEach() throws SQLException {
        userDao.deleteAll();
    }

    @Test
    @DisplayName("add(), findById() 잘 되는지")
    void addAndSelect() throws SQLException {
        String id = "01";
        User user = new User(id, "JaeHyun", "123123");

        userDao.add(user);

        User findUser = userDao.findById(id);
        assertEquals(user.getId(), findUser.getId());
        assertEquals(user.getName(), findUser.getName());
        assertEquals(user.getPassword(), findUser.getPassword());
    }

    @Test
    @DisplayName("findById() 실패 시나리오")
    void findByIdFail() throws SQLException {
        String id = "01";
        assertThrows(NoSuchElementException.class, () -> userDao.findById(id));
    }

    @Test
    @DisplayName("getCount() 잘 되는지")
    void count() throws SQLException {
        User user1 = new User("01", "user1", "111");
        User user2 = new User("02", "user2", "222");
        User user3 = new User("03", "user3", "333");

        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        userDao.add(user1);
        assertEquals(userDao.getCount(), 1);

        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);

        userDao.add(user3);
        assertEquals(userDao.getCount(), 3);
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