package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.mvc.dto.Trade;
import cn.stylefeng.guns.huobi.response.HistoryTradeResponse;
import cn.stylefeng.guns.modular.huobi.dao.TradeDetailMapper;
import cn.stylefeng.guns.modular.huobi.model.HistoryTradeDetail;
import cn.stylefeng.guns.modular.huobi.dao.HistoryTradeDetailMapper;
import cn.stylefeng.guns.modular.huobi.model.TradeDetail;
import cn.stylefeng.guns.modular.huobi.service.IHistoryTradeDetailService;
import cn.stylefeng.guns.modular.huobi.service.ITradeDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import net.sf.ehcache.hibernate.management.impl.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class HistoryTradeDetailServiceImpl extends ServiceImpl<HistoryTradeDetailMapper, HistoryTradeDetail> implements IHistoryTradeDetailService {
    private static final Logger logger = LoggerFactory.getLogger(HistoryTradeDetailServiceImpl.class);
    @Autowired
    private ITradeDetailService tradeDetailService;
    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ,readOnly = false)
    public HistoryTradeResponse getAndInsertHistoryTradeData(ApiClient client){
        //ethusdt
        HistoryTradeResponse historyTradeResponse = client.historyTrade("btcusdt", "20");
        //获取返回响应的json对象的 historyTrade data
        List<Map> historyTradeMapList = (List<Map>) historyTradeResponse.getData();
        //获取data里的data
        List<TradeDetail> insertTradeDetailList = Lists.newArrayList();

//        List<Long> insertBargainIds = Lists.newArrayList();
        for (Map tradeMap:historyTradeMapList) {
            List<Map> historyTradeDetailList = (List<Map>) tradeMap.get("data");
            //两个id不同bargainId是成交id(里面的),id消息id，外面的
            if (historyTradeDetailList.size()==1){
                TradeDetail tradeDetail = BeanUtil.mapToBean(historyTradeDetailList.get(0), TradeDetail.class, false);

                setInsertTradeDetailList(tradeDetail,tradeMap,insertTradeDetailList);
            } else {
                for (Map tradeDetailMap:historyTradeDetailList) {
                    TradeDetail tradeDetail = BeanUtil.mapToBean(tradeDetailMap, TradeDetail.class, false);
                    setInsertTradeDetailList(tradeDetail,tradeMap,insertTradeDetailList);
                }
            }
        }

        //List<Long> existBargainIds =
        List<TradeDetail> existTradeDetail = tradeDetailService.selectList(null);
        if (CollectionUtils.isNotEmpty(insertTradeDetailList)){
            insertTradeDetailList.removeAll(existTradeDetail);
        }
        if (CollectionUtils.isNotEmpty(insertTradeDetailList)){
            boolean insertBatch = tradeDetailService.insertBatch(insertTradeDetailList);
            if (!insertBatch)
                logger.error("tradeDetail历史成交 插入失败");
        }

        return historyTradeResponse;
    }

    private void setInsertTradeDetailList(TradeDetail historyTradeDetail,Map tradeMap, List<TradeDetail> insertTradeDetailList){

        historyTradeDetail.setBargainId(historyTradeDetail.getId());
        historyTradeDetail.setId((long)tradeMap.get("id"));
        insertTradeDetailList.add(historyTradeDetail);
    }
}
