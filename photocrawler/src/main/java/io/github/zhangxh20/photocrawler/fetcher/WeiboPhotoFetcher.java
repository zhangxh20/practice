package io.github.zhangxh20.photocrawler.fetcher;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WeiboPhotoFetcher extends AbstractFetcher{

    @Override
    public List<String> parse(String data) {
        JSONObject obj = JSON.parseObject(data);
        JSONObject d = obj.getJSONObject("data");
        JSONArray photoList = d.getJSONArray("photo_list");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < photoList.size(); i++) {
            JSONObject item = photoList.getJSONObject(i);
            list.add("http://wx2.sinaimg.cn/large/" + item.getString("pid")+ ".jpg");
        }
        return list;
    }
    
    public String getCookie() {
        return "SINAGLOBAL=1379642884596.7922.1498066084608; "
                + "UM_distinctid=15d74ba4b598e2-0024a91173e61e-701238-384000-15d74ba4b5a62b; "
                + "UOR=www.ijjnews.com,widget.weibo.com,baike.baidu.com; wvr=6; USRANIME=usrmdinst_29; "
                + "SSOLoginState=1503244930; SCF=AmVsl-JZHPA9Zm8Rf0VL6m9uT47AmKlxnHepDEagFlHNNNUhsAtyxvUITPDRkLR-73ABURMapN2yIJWd68SwQo8.; "
                + "SUB=_2A250ncLSDeThGeRO7lsW-S3PyD6IHXVX6rMarDV8PUNbmtBeLRX1kW93Ia3ip6syTkylIanJXEERjd86JQ..; "
                + "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWngkWNTU.RN.8a3vgyWvCv5JpX5KMhUgL."
                + "Foz7SK.N1Ke0e0z2dJLoI0YLxK.L1K2LB-zLxKqLBKeLB--LxKqLBoMLB.eLxKML1hBLBo2LxK-L1--L1-2LxKnLB.BL1-qLxKnL1-qLBozt; "
                + "SUHB=0A0YCb47d6Ax0R; ALF=1534780929; _s_tentry=-; Apache=5086867342113.386.1503244995930; "
                + "ULV=1503244995973:20:7:4:5086867342113.386.1503244995930:1503241403671";
    }

    @Override
    public String getAccept() {
        // TODO Auto-generated method stub
        return null;
    }

}
