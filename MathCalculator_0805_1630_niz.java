// 代码生成时间: 2025-08-05 16:30:44
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * MathCalculator - A utility class for mathematical calculations.
 * This class demonstrates the use of MyBatis for simple CRUD operations.
 */
@Mapper
public interface MathCalculator {

    // SQL query to find the sum of two numbers
    @Select("SELECT #{a} + #{b} AS sum")
    BigDecimal sum(@Param("a") BigDecimal a, @Param("b") BigDecimal b);

    // SQL query to find the difference of two numbers
    @Select("SELECT #{a} - #{b} AS difference")
    BigDecimal subtract(@Param("a") BigDecimal a, @Param("b") BigDecimal b);

    // SQL query to find the product of two numbers
    @Select("SELECT #{a} * #{b} AS product")
    BigDecimal multiply(@Param("a") BigDecimal a, @Param("b") BigDecimal b);

    // SQL query to find the quotient of two numbers
    @Select("SELECT #{a} / #{b} AS quotient")
    BigDecimal divide(@Param("a") BigDecimal a, @Param("b") BigDecimal b);

    /**
     * Perform mathematical operations.
     *
     * @param operation The operation to perform (sum, subtract, multiply, divide).
     * @param a The first number.
     * @param b The second number.
     * @return The result of the operation.
     * @throws IllegalArgumentException If the operation is not supported or if division by zero occurs.
     */
    default BigDecimal performOperation(String operation, BigDecimal a, BigDecimal b) {
        switch (operation.toLowerCase()) {
            case "sum":
                return sum(a, b);
            case "subtract":
                return subtract(a, b);
            case "multiply":
                return multiply(a, b);
            case "divide":
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed.");
                }
                return divide(a, b);
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operation);
        }
    }
}
