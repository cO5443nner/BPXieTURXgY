// 代码生成时间: 2025-08-12 20:34:21
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for generating Excel reports based on data fetched from a database using MyBatis.
 * It demonstrates a simple use case of integrating MyBatis with Apache POI to create Excel files.
 */
public class ExcelGenerator {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor to initialize the SqlSessionFactory.
     */
    public ExcelGenerator() {
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(