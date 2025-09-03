// 代码生成时间: 2025-09-03 11:57:20
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.text.StringEscapeUtils;

/**
 * 服务类，提供XSS攻击防护功能。
 */
public class XssProtectionService {

    /**
     * 清除XSS攻击代码。
     * 
     * @param input 输入的字符串，可能包含XSS攻击代码。
     * @return 清除XSS攻击代码后的字符串。
     */
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        try {
            // 使用 Apache Commons Text 库进行XSS清理
            String cleanInput = StringEscapeUtils.escapeHtml4(input);
            // 进一步使用正则表达式来过滤标签和JavaScript
            return removeScriptTagsAndJavaScript(cleanInput);
        } catch (Exception e) {
            // 错误处理
            System.err.println("Error sanitizing input: " + e.getMessage());
            return null;
        }
    }

    /**
     * 使用正则表达式移除脚本标签和JavaScript代码。
     * 
     * @param input 输入的字符串。
     * @return 移除脚本标签和JavaScript代码后的字符串。
     */
    private String removeScriptTagsAndJavaScript(String input) {
        String patternScript = "<script[^>]*?>.*?</script>|<script>.*?</script>;";
        String patternJavaScript = "javascript:[^;]*?;";

        // 移除脚本标签
        Pattern pScript = Pattern.compile(patternScript, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher mScript = pScript.matcher(input);
        String cleanInput = mScript.replaceAll("");

        // 移除JavaScript代码
        Pattern pJavaScript = Pattern.compile(patternJavaScript, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher mJavaScript = pJavaScript.matcher(cleanInput);
        return mJavaScript.replaceAll("");
    }
}
