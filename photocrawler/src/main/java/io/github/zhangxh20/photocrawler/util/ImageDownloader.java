package io.github.zhangxh20.photocrawler.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageDownloader implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ImageDownloader.class);
    
    private CloseableHttpClient httpClient;
    
    private String imgUrl;
    
    public ImageDownloader(CloseableHttpClient httpClient,String imgUrl) {
        this.httpClient = httpClient;
        this.imgUrl = imgUrl;
    }

    @Override
    public void run() {
        HttpGet get = new HttpGet(imgUrl);
        OutputStream out = null;
        try {
            HttpResponse res = httpClient.execute(get);
            HttpEntity entity = res.getEntity();
            InputStream in = entity.getContent();
            String fileName= ConfigReader.getString("image.ouput.path","D:/tmp/") + System.currentTimeMillis() + ".jpg";
            out = new FileOutputStream(fileName);
            
            byte [] buffer = new byte [1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer,0,length);
            }
            logger.info("下载文件:" + fileName);
            int count = ConfigReader.counter.incrementAndGet();
            logger.info("已经下载数量:" + count);
        } catch (Exception e) {
            logger.info("exception",e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 

}
