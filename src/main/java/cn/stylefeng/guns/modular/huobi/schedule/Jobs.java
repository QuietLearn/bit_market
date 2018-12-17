package cn.stylefeng.guns.modular.huobi.schedule;

import cn.stylefeng.guns.huobi.api.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Jobs {
    public final static long HALF_MINUTES =  30 * 1000;

    @Autowired
    private Main main;
    @Scheduled(fixedDelay=HALF_MINUTES)
    public void fixedDelayJob(){
        main.apiSample();
    }
}
