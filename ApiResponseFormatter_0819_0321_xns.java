// 代码生成时间: 2025-08-19 03:21:47
public class ApiResponseFormatter {
# TODO: 优化性能

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";

    // Constructor is private to prevent instantiation
    private ApiResponseFormatter() {
    }

    /**
     * Formats a successful API response.
     *
     * @param data The data to be included in the response.
     * @return A formatted JSON string representing a successful response.
     */
    public static String formatSuccessResponse(Object data) {
        return formatResponse(STATUS_SUCCESS, data);
    }

    /**
# 优化算法效率
     * Formats an error API response.
     *
     * @param errorMessage The error message to be included in the response.
# TODO: 优化性能
     * @return A formatted JSON string representing an error response.
     */
    public static String formatErrorResponse(String errorMessage) {
        return formatResponse(STATUS_ERROR, errorMessage);
    }

    /**
     * Helper method to format the response.
     *
     * @param status The status of the response (success or error).
     * @param content The content of the response (data or error message).
     * @return A formatted JSON string.
     */
    private static String formatResponse(String status, Object content) {
        // Create a response map
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", content);
# 扩展功能模块

        // Use a JSON library to convert the map to a JSON string
        return new Gson().toJson(response);
# 改进用户体验
    }
}
