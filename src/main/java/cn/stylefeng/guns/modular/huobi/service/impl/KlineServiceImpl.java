package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.KlineResponse;
import cn.stylefeng.guns.modular.huobi.model.Kline;
import cn.stylefeng.guns.modular.huobi.dao.KlineMapper;
import cn.stylefeng.guns.modular.huobi.service.IKlineService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class KlineServiceImpl extends ServiceImpl<KlineMapper, Kline> implements IKlineService {
    private Logger logger = LoggerFactory.getLogger(KlineServiceImpl.class);
    /**
     * 调用接口 获取K线数据 并插入到数据库
     * @param client
     */
    public KlineResponse getAndInsertKlineData(ApiClient client){
        KlineResponse klineResponse = client.kline("btcusdt", "5min", "100");

        List<Kline> insertKlineList = (List<Kline>) klineResponse.getData();
        //获取symbol
        String ch = klineResponse.getCh();
        String symbol = ch.substring(ch.indexOf("market.") + "market.".length(), ch.indexOf(".kline"));

        String period = ch.substring( ch.indexOf("kline.") + "kline.".length());



        for (Kline kline : insertKlineList) {
            kline.setSymbol(symbol);
            kline.setPeroid(period);
            kline.setGmtCreated(new Date());
            kline.setGmtUpdated(new Date());
            //kline.setGmtUpdated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").(kline.getId()*1000));
        }

        List<Kline> existKlinelist = this.selectList(null);

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
}
