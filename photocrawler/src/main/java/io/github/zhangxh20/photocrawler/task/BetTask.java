package io.github.zhangxh20.photocrawler.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.zhangxh20.photocrawler.App;
import io.github.zhangxh20.photocrawler.util.ConfigReader;
import io.github.zhangxh20.photocrawler.util.RegUtil;

public class BetTask {
    
    private Logger logger = LoggerFactory.getLogger(BetTask.class);

    protected CloseableHttpClient httpClient;
    
    private int betIndex = -1;
    
    public int number = -1;

    public int issue = 1;
    
    public int sleepTime = 0;
    
    private final static BigDecimal CONST_TIME = new BigDecimal("0.03");
    
    public BigDecimal times = CONST_TIME;
    
    private String url = "http://103.46.139.100:666/Product/ProductSscOrder";
    
    private boolean joined = false;

    private int loseTimes = 0;
    
    private Label lab = null;
    
    private int winTimes;
    
    private String name;
    
    public BetTask(String name) {
        this.name = name;
    }

    public void bet(String lotNo) {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
        if (!selectIndex()) {
            return;
        }
        if (joined) {
            if (lab.getLose() == 0) {
                issue = 1;
                loseTimes = 0;
                winTimes ++;
                times = times.multiply(new BigDecimal("2.0")).setScale(2,BigDecimal.ROUND_HALF_UP);
                logger.info("【{}】================= 中奖了！！！当前连中{}=================",name,winTimes);
                if (winTimes == 6) {
                    logger.info("【{}】================= 推波成功！！！=================",name);
                    times = CONST_TIME;
                    winTimes = 0;
                }
            } else  {
                loseTimes ++;
                times = CONST_TIME;
                winTimes = 0;
                logger.info("【{}】---------未中奖ORZ，当前未中奖次数：{}-----------",name,loseTimes);
            }
        }
        logger.info("【{}】上期号码:{}",name,lab.getPre());
        // 投注
        betR(lab.getPre(),lotNo);
        joined = true;
        issue ++ ;
    }
    
    public void betR(int cur,String lotNo) {
        HttpPost post = new HttpPost(url);
        String data = null;
        try {
            lotNo = String.valueOf(Long.parseLong(lotNo) + 1);
            post.setHeader("cookie", getCookie());
            post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.setHeader("Referer", "http://103.46.139.100:666/Main/LotteryIndex/976");
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("issueNo", lotNo));
            params.add(new BasicNameValuePair("lotteryId", "12"));
            params.add(new BasicNameValuePair("playTypeId", "28"));
            params.add(new BasicNameValuePair("model", "1850"));
            params.add(new BasicNameValuePair("dicWs", ""));
            params.add(new BasicNameValuePair("mul", String.valueOf(times.doubleValue())));
            params.add(new BasicNameValuePair("buyNums",RegUtil.genCode(betIndex, cur,0)));
            post.setEntity(new UrlEncodedFormEntity(params));
            logger.info("【{}】开始投注，当前投注：{},期次：{},倍投：{},期号:{},投注码：{}",
                    name,RegUtil.name(betIndex),issue,times,lotNo,RegUtil.genCode(betIndex, cur,0));
            HttpResponse res = httpClient.execute(post);
            HttpEntity entity = res.getEntity();
            data = EntityUtils.toString(entity);
            logger.info("【{}】投注返回信息:{}",name,data);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int random() {
        return new Random().nextInt(2);
    }

    
    public String getCookie() {
        return ConfigReader.getString("boyue");
    }

    public int getBetIndex() {
        return betIndex;
    }

    public void setBetIndex(int betIndex) {
        logger.info("【{}】选择投注" + RegUtil.name(betIndex),name);
        this.betIndex = betIndex;
    }
    
    public boolean selectIndex() {
        if (betIndex != -1) {
            if (this.lab == null) {
                this.lab = App.map.get(betIndex);
            }
            return true;
        } else {
            return false;
        }
    }
}
