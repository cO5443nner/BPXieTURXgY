// 代码生成时间: 2025-08-20 00:12:21
import org.apache.commons.text.StringEscapeUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.io.IOException;

/**
 * 类名: XssProtection
 * 功能: 提供XSS攻击防护
 * 作者: 你的姓名（或者你的ID）
 * 时间: 2023年12月6日
 */
public class XssProtection {

    private SqlSessionFactory sqlSessionFactory;

    public XssProtection(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 清理输入，防止XSS攻击
     * @param input 用户输入
     * @return 清理后的字符串
     */
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return Jsoup.clean(input, Safelist.basic());
    }

    /**
     * 从数据库查询用户数据，同时进行XSS防护
     * @param userId 用户ID
     * @return 用户数据
     */
    public String getUserById(int userId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            String userData = userMapper.getUserById(userId);
            return sanitizeInput(userData);
        } catch (IOException e) {
            // 处理错误情况
            System.err.println("数据库查询错误: " + e.getMessage());
            return null;
        }
    }
}

/**
 * MyBatis Mapper接口
 */
@Mapper
interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{userId}")
    String getUserById(int userId);
}
