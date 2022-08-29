package com.lg.zhu.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lg.zhu.bean.Weather;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cVzhanshi
 * @create 2022-08-04 22:02
 */
public class WeatherUtils {
    public static void main(String[] args) {
        System.out.println(getWeather());
    }
    public static Weather getWeather(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<String,String>();
        map.put("district_id","411621"); // 地方行政代码
        map.put("data_type","all");//这个是数据类型
        map.put("ak","Y7tkOEZ6HRubGuskvglEM6RiwDqpPnEU");
        String res = restTemplate.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class,
                map);
        JSONObject json = JSONObject.parseObject(res);
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");//获取预报数据
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");//实况数据
        Weather weather = weathers.get(0);
        weather.setText_now(now.getString("text"));//天气现象
        weather.setTemp(now.getString("temp"));//实时温度
        weather.setWind_class(now.getString("wind_class"));//风力等级
        weather.setWind_dir(now.getString("wind_dir"));//风向描述
        return weather;
    }
}

