// 代码生成时间: 2025-08-18 13:36:53
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * MyBatis集成测试类
 * 这个类用于测试MyBatis映射器的功能
 */
public class MyBatisIntegrationTest {

    /**
     * 获取MyBatis SqlSessionFactory对象
     */
    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 测试获取用户列表的方法
     */
    @Test
    public void testSelectUsers() throws IOException {
        try (SqlSession session = getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectUsers();
            assertEquals(3, users.size());
        }
    }

    /**
     * 测试插入用户的方法
     */
    @Test
    public void testInsertUser() throws IOException {
        try (SqlSession session = getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User newUser = new User("John Doe", 30);
            mapper.insertUser(newUser);
            session.commit();
            User fetchedUser = mapper.selectUserById(newUser.getId());
            assertEquals(newUser.getName(), fetchedUser.getName());
        }
    }

    /**
     * MyBatis映射器接口
     */
    public interface UserMapper {
        List<User> selectUsers();
        User selectUserById(int id);
        void insertUser(User user);
    }

    /**
     * 用户实体类
     */
    public static class User {
        private int id;
        private String name;
        private int age;

        public User() {
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
