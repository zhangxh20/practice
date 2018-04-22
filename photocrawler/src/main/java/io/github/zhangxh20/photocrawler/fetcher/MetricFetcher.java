package io.github.zhangxh20.photocrawler.fetcher;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.zhangxh20.photocrawler.util.ConfigReader;

public class MetricFetcher extends AbstractFetcher{

    @Override
    public List<String> parse(String data) {
        Document doc = Jsoup.parse(data);
        Elements eleList = doc.select("#chartsTable > tbody > tr");
        List<String> list = new ArrayList<>();
        for (Element e : eleList) {
            Elements n = e.select("td > div.wth");
            String str = "";
            if (n.size() == 0) {
                continue;
            }
            for (Element i : n) {
                str += i.ownText();
            }
            n = e.select("td.issue");
            for (Element i : n) {
                str += "," + i.ownText();
            }
            list.add(str);
        }
        return list;
    }

    @Override
    public String getCookie() {
        return ConfigReader.getString("boyue");
    }

    @Override
    public String getAccept() {
        return "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
    }

}
