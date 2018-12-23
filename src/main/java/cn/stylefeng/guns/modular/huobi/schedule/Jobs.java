package cn.stylefeng.guns.modular.huobi.schedule;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.api.Main;
import cn.stylefeng.guns.huobi.constant.HuobiConst;
import cn.stylefeng.guns.huobi.util.KLineCombineChart;
import cn.stylefeng.guns.modular.huobi.service.IKlineDivideService;
import cn.stylefeng.guns.modular.huobi.service.IKlineService;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Component
public class Jobs {
    private static final Logger logger = LoggerFactory.getLogger(Jobs.class);

    public final static long HALF_MINUTES =  30 * 1000;

    @Autowired
    private Main main;
    @Autowired
    private KLineCombineChart kLineCombineChart;
    @Autowired
    private IKlineDivideService klineDivideService;
    @Autowired
    private IKlineService klineService;



    //该异步会和主界面调用的klineservice方法冲突，造成数据库死锁，主界面在插入时，它进行查找
    @Scheduled(initialDelay=60000,fixedDelay=HALF_MINUTES)
    @Async
    public void fixedDelayJob(){
        logger.info("job");
        main.apiSample();
    }

    @Scheduled(initialDelay=60000, fixedDelay=HALF_MINUTES)
    @Async
    public void autoRefresh(){
        logger.info("auto");
        kLineCombineChart.autoRefresh();
    }

    @Scheduled(fixedRate=500)
    @Async
    public void insertKlineDivideData(){
        klineDivideService.getAndInsertKlineDivideData("btcusdt",new ApiClient(main.API_KEY,main.API_SECRET));
    }


    @Scheduled(fixedDelay=300*1000)
    @Async
    public void syncFiveMinData(){
        klineService.getAndInsertKlineData("btcusdt", HuobiConst.peroid.FIVE_MIN.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }
    @Scheduled(fixedDelay=60*1000)
    @Async
    public void syncOneMinData(){
        klineService.getAndInsertKlineData("btcusdt", HuobiConst.peroid.ONE_MIN.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }
    @Scheduled(fixedDelay=900*1000)
    @Async
    public void syncFifthMinData(){
        klineService.getAndInsertKlineData("btcusdt",HuobiConst.peroid.FIFTH_MIN.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }
    @Scheduled(fixedDelay= 1800 * 1000)
    @Async
    public void syncThirtyMinData(){
        klineService.getAndInsertKlineData("btcusdt",HuobiConst.peroid.THIRTY_MIN.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }
    @Scheduled(fixedDelay= 3600 *1000)
    @Async
    public void sixtyMinData(){
        klineService.getAndInsertKlineData("btcusdt",HuobiConst.peroid.SIXTY_MIN.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }
    @Scheduled(fixedDelay= 1000*60*60*24)
    @Async
    public void oneDayData(){
        klineService.getAndInsertKlineData("btcusdt",HuobiConst.peroid.ONE_DAY.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }
    @Scheduled(fixedDelay= 1000*60*60*24*7)
    @Async
    public void oneMonthData(){
        klineService.getAndInsertKlineData("btcusdt",HuobiConst.peroid.ONE_MON.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }
    @Scheduled(fixedDelay= 1000*60*60*24*7)
    @Async
    public void oneWeekData(){
        klineService. getAndInsertKlineData("btcusdt",HuobiConst.peroid.ONE_WEEK.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }


   /* @Scheduled(fixedDelay= 1000*60*60*24*365)
    @Async
    public void oneYearData(){
        klineService. getAndInsertKlineData("btcusdt",HuobiConst.peroid.ONE_YEAR.getPeroid(),new ApiClient(main.API_KEY,main.API_SECRET));
    }*/







    //   /1 每隔1分钟启动 s m h ? ? week(1 星期天)
    /*@Scheduled(cron = "0 0/1 * *(?) * ?")
    public void timerToNow(){
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }*/
}
