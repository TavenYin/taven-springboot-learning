package com.github.taven.websocket.util;

import java.util.HashMap;
import java.util.Map;

public class QueryStringUtil {

    public static Map<String, String> parse(String querystring) {
        Map<String, String> map = new HashMap<>();
        String[] arrSplit = querystring.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                map.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    map.put(arrSplitEqual[0], "");
                }
            }
        }
        return map;
    }

}
