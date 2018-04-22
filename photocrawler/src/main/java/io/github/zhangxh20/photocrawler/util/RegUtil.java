package io.github.zhangxh20.photocrawler.util;

import java.util.Random;

public class RegUtil {

    private static String [][] codes = {{"1289","3467"},{"0239","4578"},{"0134","5689"},{"1245","0679"},{"2356","0178"},
            {"3467","1289"},{"4578","0239"},{"5689","0134"},{"0679","1245"},{"0178","2356"}};
    public static int charToInt(char c) {
        char [] cs = {c};
        String str = new String(cs);
        return Integer.parseInt(str);
    }
    
    public static int strToInt(String str,int index) {
        char c = str.charAt(index);
        return charToInt(c);
    }
    
    public static int hit(int pre,int cur,int mode) {
        int m = Math.abs(pre - cur);
        if (mode == 0) {
            if (m == 1 || m == 2 || m == 9 || m == 8) {
                return 1;
            }
        } else {
            if (m == 3 || m == 4 || m == 6 || m == 7) {
                return 1;
            }
        }
        if (m == 0 || m == 5) {
            return 0;
        } else {
            return 2;
        }
        
    }
    
    public static String name(int i) {
        if (i == 0) {
            return "万位";
        } else if (i == 1) {
            return "千位";
        } else if (i == 2) {
            return "百位";
        } else if (i == 3) {
            return "十位";
        } else if (i == 4) {
            return "个位";
        } else {
            return "";
        }
    }
    
    public static int findMode(int a,int b) {
        int m = Math.abs(a - b);
        int mode  = 2;
        if (m == 1 || m == 2 || m == 9 || m == 8) {
            mode = 0;
        }
        if (m == 3 || m == 4 || m == 6 || m == 7) {
            mode = 1;
        }
        return mode;
    }
    
    public static String genCode(int index,int num,int mode) {
        String str = codes[num][mode];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i == index) {
                sb.append(str);
            }
            if (i < 4) {
                sb.append(","); 
            }
        }
        return sb.toString();
    }
    
    public static int changeIndex(int i) {
        while(true) {
            int random = new Random().nextInt(5);
            if (i != random) {
                i = random;
                break;
            }
        }
        return i;
    }
    
    public static void main(String[] args) {
         
         
    }
}
