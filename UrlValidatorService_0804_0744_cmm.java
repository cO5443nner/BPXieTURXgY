// 代码生成时间: 2025-08-04 07:44:08
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Service class for validating URL links using Java and MyBatis.
 */
public class UrlValidatorService {

    /**
     * Validates a URL to check if it's a valid HTTP or HTTPS URL.
     *
     * @param urlString The URL string to be validated.
     * @return boolean True if the URL is valid, false otherwise.
     */
    public boolean isValidUrl(String urlString) {
        // URL Validator from Apache Commons Validator library
        UrlValidator urlValidator = new UrlValidator(new String[] { "http", "https" }, UrlValidator.ALLOW_LOCAL_URLS);

        // Check if the URL is valid
        if (urlValidator.isValid(urlString)) {
            // Attempt to connect to the URL to ensure it's accessible
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(5000); // 5 seconds timeout
                connection.connect();

                // Check HTTP response code
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return true;
                }
            } catch (Exception e) {
                // Handle any exceptions such as MalformedURLException or IOException
                e.printStackTrace();
            }
        }
        return false;
    }
}
