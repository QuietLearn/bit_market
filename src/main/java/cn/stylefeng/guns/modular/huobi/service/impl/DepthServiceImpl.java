package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.request.DepthRequest;
import cn.stylefeng.guns.huobi.response.DepthResponse;
import cn.stylefeng.guns.modular.huobi.dao.OrderMapper;
import cn.stylefeng.guns.modular.huobi.model.Depth;
import cn.stylefeng.guns.modular.huobi.dao.DepthMapper;
import cn.stylefeng.guns.modular.huobi.model.Order;
import cn.stylefeng.guns.modular.huobi.service.IDepthService;
import cn.stylefeng.guns.modular.huobi.service.IOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class DepthServiceImpl extends ServiceImpl<DepthMapper, Depth> implements IDepthService {
    private Logger logger = LoggerFactory.getLogger(DepthServiceImpl.class);
    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderMapper orderMapper;
    /**
     * 调用接口获取 市场深度行情（单个特定symbol） 并插入数据库
     * @param client
     */
    public DepthResponse getAndInsertDepthData(ApiClient client){
        DepthRequest depthRequest = new DepthRequest();
        depthRequest.setSymbol("btcusdt");
        depthRequest.setType("step0");
        DepthResponse depthResponse = client.depth(depthRequest);

        Depth tick = depthResponse.getTick();

        List<List<BigDecimal>> bids = tick.getBids();

        List<List<BigDecimal>> asks = tick.getAsks();

        List<Order> insertOrderList = Lists.newArrayList();
        for (List<BigDecimal> bid:bids) {
            Order order = new Order();
            order.setPrice(bid.get(0));
            order.setAmount(bid.get(1));
            order.setType(0);
            insertOrderList.add(order);
        }

        for (List<BigDecimal> ask:asks) {
            Order order = new Order();
            order.setPrice(ask.get(0));
            order.setAmount(ask.get(1));
            order.setType(1);
            insertOrderList.add(order);
        }

        //获取买盘数据
        List<Order> existOrderList = orderService.selectList(null);
        List<Order> orders = orderMapper.selectList(null);

        if (CollectionUtils.isNotEmpty(insertOrderList)) {
            insertOrderList.removeAll(existOrderList);
        }
        if (CollectionUtils.isNotEmpty(insertOrderList)) {
            boolean insertBatch = orderService.insertBatch(insertOrderList);
            if(!insertBatch)
                logger.error("order买卖盘数据插入失败");
        }
       /* List<Depth> depthList = depthMapper.selectAll();
        if (CollectionUtils.isNotEmpty(depthList)&&depthList.contains(tick)){
            logger.info("数据库中已存在此深度行情depth");
            return;
        }*/
        return depthResponse;

    }
}
