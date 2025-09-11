// 代码生成时间: 2025-09-12 00:02:12
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * WebContentCrawler is a class to fetch and parse web content.
 * It uses Jsoup library for HTML parsing and Apache HttpClient for HTTP requests.
 *
 * @author YourName
 * @version 1.0
 */
public class WebContentCrawler {

    private CloseableHttpClient httpClient;

    /**
     * Constructor to initialize the HTTP client.
     */
    public WebContentCrawler() {
        this.httpClient = HttpClients.createDefault();
    }

    /**
     * Fetches the HTML content from the specified URL.
     *
     * @param url The URL to fetch the content from.
     * @return The HTML content as a String.
     * @throws IOException If an I/O error occurs during the retrieval.
     * @throws URISyntaxException If the URL is malformed.
     */
    public String fetchHtmlContent(String url) throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(new URI(url));
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        return entity != null ? EntityUtils.toString(entity) : "";
    }

    /**
     * Parses the HTML content to extract specific elements.
     *
     * @param htmlContent The HTML content to parse.
     * @param cssQuery The CSS query to select elements.
     * @return A list of elements matching the CSS query.
     */
    public List<Element> parseHtmlContent(String htmlContent, String cssQuery) {
        Document doc = Jsoup.parse(htmlContent);
        Elements elements = doc.select(cssQuery);
        List<Element> matchedElements = new ArrayList<>(elements.size());
        for (Element element : elements) {
            matchedElements.add(element);
        }
        return matchedElements;
    }

    /**
     * Closes the HTTP client to free up system resources.
     *
     * @throws IOException If an I/O error occurs during closing.
     */
    public void closeHttpClient() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    // Example usage
    public static void main(String[] args) {
        WebContentCrawler crawler = new WebContentCrawler();
        try {
            String url = "https://example.com";
            String htmlContent = crawler.fetchHtmlContent(url);
            List<Element> elements = crawler.parseHtmlContent(htmlContent, "div.container");
            for (Element element : elements) {
                System.out.println(element.text());
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                crawler.closeHttpClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
