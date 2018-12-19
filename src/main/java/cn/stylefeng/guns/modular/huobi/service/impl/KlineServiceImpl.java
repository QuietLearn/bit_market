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
    private String size;

    /**
     * 调用接口 获取K线数据 并插入到数据库
     * @param client
     */
    public KlineResponse getAndInsertKlineData(ApiClient client){
        KlineResponse klineResponse = client.kline("btcusdt", period, size);

        List<Kline> insertKlineList = (List<Kline>) klineResponse.getData();
        //获取symbol
        String ch = klineResponse.getCh();
        String symbol = ch.substring(ch.indexOf("market.") + "market.".length(), ch.indexOf(".kline"));

        period = ch.substring( ch.indexOf("kline.") + "kline.".length());

        for (Kline kline : insertKlineList) {
            kline.setSymbol(symbol);
            kline.setPeroid(period);
            kline.setGmtCreated(new Date(kline.getId()*1000l));
            //kline.setGmtUpdated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").(kline.getId()*1000));
        }

        Wrapper wrapper = new EntityWrapper<Kline>();
        wrapper.eq("peroid",period);
        wrapper.orderBy("id",false);
        wrapper.last(size);

        List<Kline> existKlinelist = klineMapper.selectInserted(period,size);

        if (CollectionUtils.isNotEmpty(insertKlineList)){
            insertKlineList.removeAll(existKlinelist);
        }
        if (CollectionUtils.isNotEmpty(insertKlineList)){
            boolean insertBatch = this.insertBatch(insertKlineList);
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
