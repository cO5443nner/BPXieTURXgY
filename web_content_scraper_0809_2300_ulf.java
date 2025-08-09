// 代码生成时间: 2025-08-09 23:00:59
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebContentScraper {

    // 抓取网页内容的方法
    public static String scrapeWebContent(String urlString) {
        try {
            // 使用HttpClient获取网页内容
            URL url = new URL(urlString);
            String encodedUrl = URLEncoder.encode(urlString, StandardCharsets.UTF_8.toString());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(encodedUrl);
            request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0");
            CloseableHttpResponse response = httpClient.execute(request);

            // 读取响应内容
            String content = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            response.close();
            httpClient.close();

            // 使用Jsoup解析网页内容
            Document doc = Jsoup.parse(content);
            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            // 测试抓取网页内容
            String urlToScrape = "http://example.com"; // 替换为要抓取的网页地址
            String scrapedContent = scrapeWebContent(urlToScrape);
            if (scrapedContent != null) {
                System.out.println("网页内容抓取成功: 
" + scrapedContent);
            } else {
                System.out.println("网页内容抓取失败。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
