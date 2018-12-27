package cn.stylefeng.guns.modular.huobi.controller;
import cn.stylefeng.guns.modular.huobi.model.Kline;
import cn.stylefeng.guns.modular.huobi.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RedisServiceController {
    @Autowired
    private RedisServiceImpl redisService;

    @RequestMapping(value = "/setredis")
    public String setredis(String keyredis){
        redisService.setStr(keyredis,"2018年1月26日");
        return "保存成功,请访问getredis查询redis";
    }

    @RequestMapping(value = "/setObj")
    public String setObj(String keyredis){
        Kline kline = new Kline();
      /*  kline.setSort(1);
        kline.setTimestamp(new Date().getTime());
        kline.setProductName("productname");*/
// list.add(kline);
        redisService.set(keyredis, kline);
        return "保存成功,请访问getredis查询redis";
    }

    @RequestMapping(value = "/getObj")
    public Object getObj(String keyredis){
        Object object = redisService.get(keyredis);
        if(object !=null){
            Kline kline = (Kline) object;
            System. out.println(kline.getAmount());
            System. out.println(kline.getId());
            System. out.println(kline.getOpen());
        }
        return object;
    }

    @RequestMapping(value = "/delObj")
    public boolean delObj(String keyredis){
        boolean del = redisService.del(keyredis);
        return del;
    }


    @RequestMapping(value = "/getredis")
    public String getredis(String keyredis){
        String getredis = (String) redisService.getKey(keyredis);
        return "redis的key是===>"+getredis;
    }


    @RequestMapping(value = "/delredis")
    public String delredis(String keyredis){
        redisService.delKey(keyredis);
        return "删除成功，请通过getredis进行查询";
    }





    @RequestMapping(value = "/setList")
    public String setList(String keyredis){
        List list = new ArrayList();
        for (int i = 0;i<10;i++){
            Kline kline = new Kline();
          /*  kline.setSort(1);
            kline.setTimestamp(new Date().getTime());
            kline.setProductName("productname");*/
            list.add(kline);
        }
        redisService.set(keyredis, list);
        return "保存成功,请访问getredis查询redis";
    }

    @RequestMapping(value = "/getList")
    public Object getList(String keyredis){
        Object object = redisService.get(keyredis);
        if(object !=null){
            List<Kline> klines = (List<Kline>) object;
            for (int i = 0;i<klines.size();i++){
                /*Kline kline = klines.get(i);
                System.out.println(kline.getProductName());
                System.out.println(kline.getId());
                System.out.println(kline.getTimestamp());*/
            }
        }
        return object;
    }

    @RequestMapping(value = "/delList")
    public boolean delList(String keyredis){
        boolean del = redisService.del(keyredis);
        return del;
    }


}