// 代码生成时间: 2025-10-12 02:33:25
package com.example.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * 依赖关系分析器
 * 用于分析数据库中的依赖关系
 */
public class DependencyAnalyzer {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 构造函数
     * @param resourcePath 配置文件路径
     */
    public DependencyAnalyzer(String resourcePath) {
        try {
            // 读取配置文件
            Reader reader = Resources.getResourceAsReader(resourcePath);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取依赖关系
     * @return 依赖关系列表
     */
    public List<Dependency> getDependencies() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 获取映射器
            DependencyMapper mapper = session.getMapper(DependencyMapper.class);
            // 查询依赖关系
            return mapper.listDependencies();
        }
    }

    /**
     * 主函数
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        DependencyAnalyzer analyzer = new DependencyAnalyzer("mybatis-config.xml");
        List<Dependency> dependencies = analyzer.getDependencies();
        dependencies.forEach(System.out::println);
    }
}

/**
 * 依赖关系映射器接口
 */
@Mapper
interface DependencyMapper {

    @Select("SELECT * FROM dependencies")
    List<Dependency> listDependencies();
}

/**
 * 依赖关系类
 */
class Dependency {

    private int id;
    private String source;
    private String target;

    // 省略 getter 和 setter 方法

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Dependency{" +
               "id=" + id +
               ", source='" + source + '\'' +
               ", target='" + target + '\'' +
               '}';
    }
}
