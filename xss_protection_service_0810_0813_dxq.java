// 代码生成时间: 2025-08-10 08:13:26
import org.apache.commons.text.StringEscapeUtils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class XSSProtectionService {

    private static final Pattern[] PATTERNS = {
        // Patterns to match script tags and other common XSS vectors
        Pattern.compile("<(script|iframe|style|base|object|embed|video|audio)", Pattern.CASE_INSENSITIVE),
        Pattern.compile("(javascript|vbscript|expression|url|vbs):.*?(?=\\s|\\
|$)", Pattern.CASE_INSENSITIVE),
        Pattern.compile("on(abort|blur|change|click|error|focus|load|message|mouse(?:down|move|up|over|out|enter|leave|wheel)|reset|resize|scroll|select|submit|unload)=", Pattern.CASE_INSENSITIVE)
    };

    public String sanitizeInput(String input) {
        // Sanitize the input to prevent XSS attacks
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        for (Pattern pattern : PATTERNS) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                // Replace matched XSS patterns with empty string
                input = matcher.replaceAll("");
            }
        }

        // Use Apache Commons Text to escape HTML entities
        String sanitizedInput = StringEscapeUtils.escapeHtml4(input);

        return sanitizedInput;
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        XSSProtectionService service = new XSSProtectionService();
        String unsafeInput = "<script>alert('XSS')</script>";
        try {
            String sanitizedInput = service.sanitizeInput(unsafeInput);
            System.out.println("Sanitized Input: " + sanitizedInput);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
