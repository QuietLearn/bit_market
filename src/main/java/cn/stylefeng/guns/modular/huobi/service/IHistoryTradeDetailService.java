package cn.stylefeng.guns.modular.huobi.service;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.HistoryTradeResponse;
import cn.stylefeng.guns.modular.huobi.model.HistoryTradeDetail;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
public interface IHistoryTradeDetailService extends IService<HistoryTradeDetail> {
    HistoryTradeResponse getAndInsertHistoryTradeData(ApiClient client);
}
