// 代码生成时间: 2025-10-07 02:10:30
package com.example.logistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 物流跟踪系统
 *
 * @author Your Name
 * @version 1.0
 */
public class LogisticsTrackingSystem {

    // 数据库映射器接口
    @Mapper
    public interface LogisticsMapper {

        // 查询所有物流信息
        @Select("SELECT * FROM logistics WHERE status != 'Completed'")
        List<LogisticsInfo> selectAllActiveLogistics();

        // 根据ID查询物流信息
        @Select("SELECT * FROM logistics WHERE id = #{id}")
        LogisticsInfo selectLogisticsById(@Param("id") int id);

        // 更新物流信息状态
        @Update("UPDATE logistics SET status = #{status} WHERE id = #{id}")
        int updateLogisticsStatus(@Param("id") int id, @Param("status") String status);

        // 添加新的物流信息
        @Insert("INSERT INTO logistics (order_id, customer_name, status) VALUES (#{orderId}, #{customerName}, #{status})")
        int insertLogisticsInfo(LogisticsInfo logisticsInfo);

        // 删除物流信息
        @Delete("DELETE FROM logistics WHERE id = #{id}")
        int deleteLogisticsInfo(@Param("id\) int id);
    }

    // 物流信息实体类
    public static class LogisticsInfo {
        private int id;
        private String orderId;
        private String customerName;
        private String status;

        // getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    // 服务类
    public class LogisticsService {

        private LogisticsMapper logisticsMapper;

        public LogisticsService(LogisticsMapper logisticsMapper) {
            this.logisticsMapper = logisticsMapper;
        }

        // 获取所有活跃的物流信息
        public List<LogisticsInfo> getAllActiveLogistics() {
            try {
                return logisticsMapper.selectAllActiveLogistics();
            } catch (Exception e) {
                // 错误处理
                e.printStackTrace();
                return null;
            }
        }

        // 根据ID获取物流信息
        public LogisticsInfo getLogisticsById(int id) {
            try {
                return logisticsMapper.selectLogisticsById(id);
            } catch (Exception e) {
                // 错误处理
                e.printStackTrace();
                return null;
            }
        }

        // 更新物流信息状态
        public boolean updateLogisticsStatus(int id, String status) {
            try {
                int rowsAffected = logisticsMapper.updateLogisticsStatus(id, status);
                return rowsAffected > 0;
            } catch (Exception e) {
                // 错误处理
                e.printStackTrace();
                return false;
            }
        }

        // 添加物流信息
        public boolean insertLogisticsInfo(LogisticsInfo logisticsInfo) {
            try {
                int rowsAffected = logisticsMapper.insertLogisticsInfo(logisticsInfo);
                return rowsAffected > 0;
            } catch (Exception e) {
                // 错误处理
                e.printStackTrace();
                return false;
            }
        }

        // 删除物流信息
        public boolean deleteLogisticsInfo(int id) {
            try {
                int rowsAffected = logisticsMapper.deleteLogisticsInfo(id);
                return rowsAffected > 0;
            } catch (Exception e) {
                // 错误处理
                e.printStackTrace();
                return false;
            }
        }
    }

    // 应用程序入口点
    public static void main(String[] args) {
        LogisticsService logisticsService = new LogisticsService(logisticsMapper);
        // 示例操作，实际使用时应通过依赖注入方式获取logisticsMapper实例
        LogisticsInfo logisticsInfo = new LogisticsInfo();
        logisticsInfo.setOrderId("ORD123");
        logisticsInfo.setCustomerName("John Doe");
        logisticsInfo.setStatus("Shipped");

        // 添加物流信息
        boolean isAdded = logisticsService.insertLogisticsInfo(logisticsInfo);
        if (isAdded) {
            System.out.println("Logistics info added successfully");
        } else {
            System.out.println("Failed to add logistics info");
        }
    }
}
