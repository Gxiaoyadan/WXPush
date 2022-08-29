package com.lg.zhu.push;

import com.lg.zhu.bean.Weather;
import com.lg.zhu.utils.AnniversaryUtils;
import com.lg.zhu.utils.CaiHongPiUtils;
import com.lg.zhu.utils.WBHotUtils;
import com.lg.zhu.utils.WeatherUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Map;

/**
 * @author cVzhanshi
 * @create 2022-08-04 21:09
 */
public class Pusher {

    public static void main(String[] args) {
        push();
    }
    private static String appId = "wxdb10372fee15f067";
    private static String secret = "3f96d23f15b447dc09d225cc9ea59485";



    public static void push(){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("on79B5_Qj8gfxTpr4rWBoBgf42nE")
                .templateId("v4jvL_fU89p34SD8Crt4VfYLFxWXPGNmzO6jWTTKyFc")
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        Weather weather = WeatherUtils.getWeather();
        Map<String, String> map = CaiHongPiUtils.getEnsentence();
        templateMessage.addData(new WxMpTemplateData("riqi",weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));//YYYY-MM-DD  星期X
        templateMessage.addData(new WxMpTemplateData("tianqi",weather.getText_now(),"#00FFFF"));//天气
        templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "","#173177"));//最低温度
        templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D"));//实时温度
        templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" ));//最高温度
        templateMessage.addData(new WxMpTemplateData("windclass",weather.getWind_class()+ "","#42B857" ));//风力等级
        templateMessage.addData(new WxMpTemplateData("winddir",weather.getWind_dir()+ "","#B95EA3" ));//风向描述
        templateMessage.addData(new WxMpTemplateData("lianai", AnniversaryUtils.getLianAi()+"","#FF1493"));//恋爱天
        templateMessage.addData(new WxMpTemplateData("shengri",AnniversaryUtils.getBirthday_Hai()+"","#FFA500"));//生日天

        int count = 1;
        for(String str:WBHotUtils.getWBHot()){
            templateMessage.addData(new WxMpTemplateData("hotWord"+count, str));
            //System.out.println(str);
            count++;
        }


        String beizhu = "❤";
        if(AnniversaryUtils.getLianAi() % 365 == 0){
            beizhu = "今天是恋爱" + (AnniversaryUtils.getLianAi() / 365) + "周年纪念日！";
        }
        if(AnniversaryUtils.getBirthday_Hai()  == 0){
            beizhu = "今天是生日，生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

