package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.KlineResponse;
import cn.stylefeng.guns.modular.huobi.model.Kline;
import cn.stylefeng.guns.modular.huobi.dao.KlineMapper;
import cn.stylefeng.guns.modular.huobi.service.IKlineService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@Service
@Configuration
public class KlineServiceImpl extends ServiceImpl<KlineMapper, Kline> implements IKlineService {
    private Logger logger = LoggerFactory.getLogger(KlineServiceImpl.class);

    private String period="5min";

    @Autowired
    private KlineMapper klineMapper;

    @Value("${kline.size}")
    private Integer size;

    /**
     * 调用接口 获取K线数据 并插入到数据库
     * @param client
     */
    public KlineResponse getAndInsertKlineData(ApiClient client){
        KlineResponse klineResponse = client.kline("btcusdt", period, String.valueOf(size));

        //接口返回的所有数据
        List<Kline> KlineList = (List<Kline>) klineResponse.getData();
        //实际需要插入数据库中的数据
        List<Kline> insertKlineList = Lists.newArrayList();

        //获取symbol
        String ch = klineResponse.getCh();
        String symbol = ch.substring(ch.indexOf("market.") + "market.".length(), ch.indexOf(".kline"));

        period = ch.substring( ch.indexOf("kline.") + "kline.".length());

        List<Integer> insertKlineidList = Lists.newArrayList();
        for (Kline kline : KlineList) {
            kline.setSymbol(symbol);
            kline.setPeroid(period);
            kline.setGmtCreated(new Date(kline.getId()*1000l));
            insertKlineidList.add(kline.getId());
            //kline.setGmtUpdated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").(kline.getId()*1000));
        }
        //
        List<Integer> existIdList = klineMapper.getAllIds(period,size+2);
        if(CollectionUtils.isNotEmpty(insertKlineidList)){
            insertKlineidList.removeAll(existIdList);
        }
      /*  List<Kline> existKlinelist = klineMapper.selectInserted(period,size);

         if (CollectionUtils.isNotEmpty(insertKlineList)){
            insertKlineList.removeAll(existKlinelist);
        }*/
        for (Integer id:insertKlineidList) {
            for (Kline kline:KlineList) {
                if (kline.getId() == id){
                    insertKlineList.add(kline);
                    continue;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(insertKlineList)){
            boolean insertBatch = this.insertBatch(insertKlineList,100);
            if (!insertBatch)
                logger.error("批量插入K线数据失败");
        }
        return klineResponse;
    }





    public void setPeriodFromButton(String period){
        setPeriod(period);
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
