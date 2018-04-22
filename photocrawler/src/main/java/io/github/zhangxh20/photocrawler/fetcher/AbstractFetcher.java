package io.github.zhangxh20.photocrawler.fetcher;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFetcher implements Fetcher{

    private static Logger logger = LoggerFactory.getLogger(AbstractFetcher.class);
    
    protected CloseableHttpClient httpClient;
    
    protected String fetchData(String url) {
        if (httpClient == null) {
            logger.info("初始化 httpClient");
            httpClient = HttpClients.createDefault();
        }
        logger.info("开始获取最近5期数据******************** start");
        HttpPost get = new HttpPost(url);
        String data = null;
        try {
            get.setHeader("cookie", getCookie());
            //get.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            get.setHeader("Accept", getAccept());
            HttpResponse res = httpClient.execute(get);
            HttpEntity entity = res.getEntity();
            data = EntityUtils.toString(entity);
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return data;
    }
    
    public abstract List<String> parse(String data);
    
    
    public List<String> fetchPhotoUrl(String url) {
        String data = fetchData(url);
        List<String> list = parse(data);
        return list;
    }
    
    public abstract String getCookie();
    
    public abstract String getAccept();
    
    
}
