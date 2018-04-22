package io.github.zhangxh20.photocrawler.fetcher;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.github.zhangxh20.photocrawler.util.ConfigReader;

public class QzonePhotoFetcher extends AbstractFetcher{

    @Override
    public List<String> parse(String data) {
        data = data.substring(16,data.length() - 2);
        JSONObject obj = JSON.parseObject(data);
        JSONObject d = obj.getJSONObject("data");
        JSONArray arr = d.getJSONArray("photoList");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject item = arr.getJSONObject(i);
            list.add(item.getString("origin_url"));
        }
        return list;
    }

    @Override
    public String getCookie() {
        return ConfigReader.getString("qzone.cookie");
    }

    @Override
    public String getAccept() {
        // TODO Auto-generated method stub
        return null;
    }

    
}
