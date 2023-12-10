package _11._2;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.lang.Thread.sleep;

public class ParseHTML {
    //список уникальных элементов отсортированных по возрастанию
    private static ConcurrentSkipListSet<String> links;
    private static List<String> fileExtensions = new ArrayList<>(Arrays.asList(
            ".jpg", ".jpeg", ".png", ".gif", ".webp", ".pdf", ".eps", ".xlsx", ".doc", ".pptx", ".docx", "?_ga",
            ".zip", ".mp4", ".xls", ".html"
    ));

    public static ConcurrentSkipListSet<String> getLinks(String url) {
        links = new ConcurrentSkipListSet<>();
        try {
            sleep(150);
            Connection connection = Jsoup.connect(url)
                    .ignoreHttpErrors(true)
                    .timeout(6000)
                    .followRedirects(true);
            Document document = connection.get();
            Elements elements = document.select("body").select("a");
            for (Element element : elements) {
                String link = element.absUrl("href");
                if (isLink(link) && !isFile(link)) {
                    links.add(link);
                }
            }
        } catch (InterruptedException | IOException e) {
            System.out.println(e + " - " + url);
        }
        return links;
    }

    private static boolean isLink(String link) {
        String regex = "http[s]?://[^#,\\s]*\\.?school5prv\\.ru[^#,\\s]*";
        return link.matches(regex);
    }

    private static boolean isFile(String link) {
        link = link.toLowerCase();
        if (!link.contains(".")){
            return false;
        }
        String extenstion = link.substring(link.lastIndexOf("."));
        return !extenstion.contains("/");
    }

}
