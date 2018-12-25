package cn.stylefeng.guns.modular.huobi.service;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.KlineResponse;
import cn.stylefeng.guns.modular.huobi.model.Kline;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
public interface IKlineService extends IService<Kline> {
    /**
     * 调用接口 获取K线数据 并插入到数据库
     * @param client
     */
    KlineResponse getAndInsertKlineData(String symbol,String period,ApiClient client,Integer size,boolean judgeReduce);

    //void setPeriodFromButton(String period);


}
