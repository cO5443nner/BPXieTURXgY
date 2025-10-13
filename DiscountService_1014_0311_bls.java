// 代码生成时间: 2025-10-14 03:11:24
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

// 折扣优惠系统的接口
public interface DiscountService {

    // 获取所有折扣优惠
    @Select("SELECT * FROM discounts")
    List<Discount> getAllDiscounts();

    // 根据ID获取折扣优惠
    @Select("SELECT * FROM discounts WHERE id = #{id}")
    Discount getDiscountById(@Param("id") int id);

    // 添加一个新的折扣优惠
    int addDiscount(Discount discount);

    // 更新折扣优惠信息
    int updateDiscount(Discount discount);

    // 删除折扣优惠
    int deleteDiscount(@Param("id") int id);
}

// 折扣优惠实体类
public class Discount {
    private int id;
    private String name;
    private double discountRate;
    private String description;
    private String startDate;
    private String endDate;

    // 省略getter和setter方法
}

// 折扣优惠服务类实现
public class DiscountServiceImpl implements DiscountService {

    private final SqlSessionFactory sqlSessionFactory;

    public DiscountServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("getAllDiscounts");
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Discount getDiscountById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("getDiscountById", id);
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addDiscount(Discount discount) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("insertDiscount", discount);
            return session.commit();
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateDiscount(Discount discount) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("updateDiscount", discount);
            return session.commit();
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteDiscount(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("deleteDiscount", id);
            return session.commit();
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return 0;
        }
    }
}
