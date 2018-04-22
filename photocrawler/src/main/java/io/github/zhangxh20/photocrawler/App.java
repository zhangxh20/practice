package io.github.zhangxh20.photocrawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.zhangxh20.photocrawler.fetcher.BoyueFetcher;
import io.github.zhangxh20.photocrawler.fetcher.Fetcher;
import io.github.zhangxh20.photocrawler.fetcher.QzonePhotoFetcher;
import io.github.zhangxh20.photocrawler.fetcher.WeiboPhotoFetcher;
import io.github.zhangxh20.photocrawler.task.BetTask;
import io.github.zhangxh20.photocrawler.task.Label;
import io.github.zhangxh20.photocrawler.util.ConfigReader;
import io.github.zhangxh20.photocrawler.util.ImageDownloader;
import io.github.zhangxh20.photocrawler.util.RegUtil;

/**
 * Hello world!
 *
 */
public class App {
    
    private final static Logger logger = LoggerFactory.getLogger(App.class);
    
    public static Map<Integer, Label> map;
    
    public static void main(String[] args) {
        getCode(args);
        //metric();
    }
    
    public static void showImage() {
        Fetcher fetcher = new WeiboPhotoFetcher();
        List<String> list = fetcher.fetchPhotoUrl(ConfigReader.getString("weibo.photo.url"));
        for (String str : list) {
            System.out.println("![](" + str + " \"\")");
        }
    }
    
    public static void record(List<String> list) {
        if (map == null) {
            map = new HashMap<>();
            for (int i = 0; i < 5; i++ ) {
                Label lab = new Label();
                lab.setIndex(i);
                lab.setPre(-1);
                lab.setLose(0);
                for (String str : list) {
                    int cur = charToInt(str.charAt(i));
                    if (lab.getPre() == -1) {
                        lab.setPre(cur);
                        continue;
                    }
                    int mode = RegUtil.findMode(lab.getPre(), cur);
                    lab.setPre(cur);
                    if (mode == 0) {
                        lab.setLose(0);
                    } else {
                        lab.setLose(lab.getLose() + 1); 
                    }
               }
               logger.info(RegUtil.name(i) + ",lose:" + lab.getLose());
               map.put(i, lab);
            }
        } else {
            String str = list.get(4);
            for (int i = 0; i < 5; i++) {
                Label lab = map.get(i);
                int cur = charToInt(str.charAt(i));
                if (lab.getPre() == -1) {
                    lab.setPre(cur);
                    continue;
                }
                int mode = RegUtil.findMode(lab.getPre(), cur);
                lab.setPre(cur);
                if (mode == 0) {
                    lab.setLose(0);
                } else {
                    lab.setLose(lab.getLose() + 1); 
                }
            }
        }
    }
    
    public static boolean hit(int pre,int mode) {
        return pre == mode;
    }
    
    public static void download() {
        Fetcher fetcher = new QzonePhotoFetcher();
        ExecutorService pool =  Executors.newFixedThreadPool(5);
        List<String> list = fetcher.fetchPhotoUrl(ConfigReader.getString("qzone.photo.url"));
        CloseableHttpClient client = HttpClients.createDefault();
        for (String imgUrl : list) {
            ImageDownloader downloader = new ImageDownloader(client, imgUrl);
            pool.execute(downloader);
        }
        while (ConfigReader.counter.get() < 30) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
        System.exit(0);
    }
    
    public static void getCode(String arg []) {
        final BoyueFetcher fetcher = new BoyueFetcher();
        BetTask first = new BetTask("ONE");
        BetTask second = new BetTask("TWO");
        if (arg.length == 1) {
            first.setBetIndex(Integer.parseInt(arg[0]));
        } else if (arg.length == 2) {
            first.setBetIndex(Integer.parseInt(arg[0]));
            second.setBetIndex(Integer.parseInt(arg[1]));
        }
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> list = fetcher.fetchPhotoUrl("http://103.46.139.100:666/Init/GetHistoryNums/12");
                    record(list);
                    first.bet(fetcher.getLotNo());
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    second.bet(fetcher.getLotNo());
                } catch (Exception e) {
                    logger.error("Exception",e);
                }
                
            }
        }, 0, 60,TimeUnit.SECONDS);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String str = scanner.nextLine();
            String strs [] = str.split(",");
            if (strs.length == 1) {
                first.setBetIndex(Integer.parseInt(strs[0]));
            } else if (strs.length == 2) {
                
            }
        }
        scanner.close();
    }
    
    public static int findMode(List<String> list,int index) {
        String cur = list.get(0);
        String pre = list.get(1);
        if (index == -1) {
            return 2;
        }
        int a = charToInt(cur.charAt(index));
        int b = charToInt(pre.charAt(index));
        int m = Math.abs(a - b);
        int mode  = 2;
        if (m == 1 || m == 2 || m == 9 || m == 8) {
            mode = 0;
        }
        if (m == 3 || m == 4 || m == 6 || m == 7) {
            mode = 1;
        }
        logger.info("上一期模式：{}",mode);
        return mode;
    }
    
    public static int charToInt(char c) {
        char [] cs = {c};
        String str = new String(cs);
        return Integer.parseInt(str);
    }
    
    public static int strToInt(String str,int index) {
        if (index == -1) {
            return -1;
        }
        char c = str.charAt(index);
        return charToInt(c);
    }
}
