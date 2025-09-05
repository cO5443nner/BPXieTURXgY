// 代码生成时间: 2025-09-05 19:46:31
package crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * A simple web content crawler that fetches and parses web page content.
 */
public class WebContentCrawler {

    /**
     * Fetches the content of a given URL and extracts specific elements.
     *
     * @param urlString The URL to fetch content from.
     * @param cssQuery  The CSS query to extract specific elements.
     * @return A string containing the extracted content.
     */
    public String fetchAndExtractContent(String urlString, String cssQuery) {
        try {
            // Create an HTTP client and perform a GET request to fetch the webpage content
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            // Check if the response is valid
            if (entity != null) {
                String html = EntityUtils.toString(entity);
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select(cssQuery);

                // Concatenate all selected elements' text content
                StringBuilder contentBuilder = new StringBuilder();
                for (Element element : elements) {
                    contentBuilder.append(element.text());
                    contentBuilder.append("
");
                }
                return contentBuilder.toString().trim();
            } else {
                return "No content found.";
            }
        } catch (IOException e) {
            // Handle exceptions, such as connection errors or timeouts
            return "Error fetching content: " + e.getMessage();
        } finally {
            // Close the HTTP client to release resources
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        WebContentCrawler crawler = new WebContentCrawler();
        String url = "http://example.com";
        String cssQuery = "h1"; // Example CSS query to extract all h1 elements
        String content = crawler.fetchAndExtractContent(url, cssQuery);
        System.out.println(content);
    }
}
