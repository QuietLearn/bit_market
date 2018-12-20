package cn.stylefeng.guns.modular.huobi.schedule;

import cn.stylefeng.guns.huobi.api.Main;
import cn.stylefeng.guns.huobi.util.KLineCombineChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Component
public class Jobs {
    public final static long HALF_MINUTES =  30 * 1000;

    @Autowired
    private Main main;
    @Autowired
    private KLineCombineChart kLineCombineChart;

    @Scheduled(fixedDelay=HALF_MINUTES)
    @Async
    public void fixedDelayJob(){
        main.apiSample();
    }

    @Scheduled(initialDelay=10000, fixedDelay=HALF_MINUTES)
    @Async
    public void fixedDelayJob2(){
        kLineCombineChart.autoRefresh();
    }

    //   /1 每隔1分钟启动 s m h ? ? week(1 星期天)
    /*@Scheduled(cron = "0 0/1 * *(?) * ?")
    public void timerToNow(){
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }*/
}
