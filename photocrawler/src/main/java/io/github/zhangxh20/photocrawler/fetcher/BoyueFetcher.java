package io.github.zhangxh20.photocrawler.fetcher;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.github.zhangxh20.photocrawler.util.ConfigReader;

public class BoyueFetcher extends AbstractFetcher {
    
    private String lotNo;

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    @Override
    public List<String> parse(String data) {
        JSONObject obj = JSON.parseObject(data);
        JSONArray arr = obj.getJSONArray("def");
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            JSONObject item = arr.getJSONObject(i);
            String lotNum = item.getString("LotteryNums");
            list.offerFirst(lotNum);
        }
        lotNo = arr.getJSONObject(0).getString("IssueNo");
        return list;
    }

    @Override
    public String getCookie() {
        return ConfigReader.getString("boyue");
    }

    @Override
    public String getAccept() {
        return "application/json, text/javascript, */*; q=0.01";
    }

}
