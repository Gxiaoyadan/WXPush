package com.lg.zhu.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WBHotUtils {
    public static void main(String[] args) {
        for(String str:getWBHot()){
            System.out.println(str);
        }

    }
    public static String[] getWBHot() {
        String httpUrl = "http://api.tianapi.com/weibohot/index?key=f8fa8134c2ae90e5a568d1591421d741";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        String[] content = new String[10];
        for (int i=0;i<content.length;i++){
            content[i] = i+1+"."+newslist.getJSONObject(i).getString("hotword");
        }

        return content;
    }
}
