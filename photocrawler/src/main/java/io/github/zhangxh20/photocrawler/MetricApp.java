package io.github.zhangxh20.photocrawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import io.github.zhangxh20.photocrawler.fetcher.MetricFetcher;
import io.github.zhangxh20.photocrawler.util.RegUtil;

public class MetricApp {

    public static void main(String[] args) throws Exception {
        //insert();
        analyse();
    }

    public static int charToInt(char c) {
        char[] cs = { c };
        String str = new String(cs);
        return Integer.parseInt(str);
    }

    public static void analyse() throws Exception {
       
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC&characterEncoding=utf-8&useSSL=false", "root",
                "111111");
        String sql = "select code,issue from fenfencai order by issue asc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.execute();
        ResultSet rst = pstmt.getResultSet();
        List<String> list = new ArrayList<String>();
        while (rst.next()) {
            list.add(rst.getString(1) + "," + rst.getString(2));
        }
        for (int i = 0; i < 5; i++) {
            int index = i;
            int pre = -1;
            int win = 0;
            int issue = 0;
            int lian = 0;
            int lose = 0;
            System.out.println("=========" + RegUtil.name(index) + " =============");
            for (String str : list) {
                String[] strs = str.split(",");
                int cur = charToInt(strs[0].charAt(index));
                if (pre == -1) {
                    pre = cur;
                    continue;
                }
                int mode = RegUtil.findMode(pre, cur);
                if (mode == 0) {
                    win++;
                    lian++;
                    lose++;
                    System.out.println("第" + lose + "期中" + strs[1]);
                    lose = 0;
                } else {
                    if (lian > 0) {
                        System.out.println("连中" + lian);
                        lian = 0;
                    }
                    lose++;
                }
                pre = cur;
                issue++;
            }
            System.out.println(
                    "总期数:" + issue + ",中奖次数:" + win + "，中奖率:" + Double.valueOf("" + win) / Double.valueOf("" + issue));
        }
    }

    public static void insert() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC&characterEncoding=utf-8&useSSL=false", "root",
                "111111");
        String sql = "insert into fenfencai(issue,code) values(?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        MetricFetcher fetcher = new MetricFetcher();
        List<String> list = fetcher.fetchPhotoUrl("http://103.46.139.100:666/init/history?lottery=12&size=300");
        for (String str : list) {
            String[] strs = str.split(",");
            try {
                pstmt.setString(1, strs[1]);
                pstmt.setString(2, strs[0]);
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

    }
}
