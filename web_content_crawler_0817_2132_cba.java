// 代码生成时间: 2025-08-17 21:32:47
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A simple web content crawler that fetches and extracts content from a given URL.
 */
public class WebContentCrawler {

    /**
     * Fetches the content from the given URL and returns the raw HTML content.
     *
     * @param url The URL from which to fetch the content.
     * @return The raw HTML content as a String.
     * @throws IOException If an I/O error occurs during the retrieval.
     */
    public String fetchContent(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URI.create(url));
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            return EntityUtils.toString(entity);
        } else {
            throw new IOException(