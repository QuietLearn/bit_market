package cn.stylefeng.guns.modular.huobi.service;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.MergedResponse;
import cn.stylefeng.guns.modular.huobi.model.Merged;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
public interface IMergedService extends IService<Merged> {
    /**
     * 调用接口获取特定聚合行情(Ticker) 并插入到数据库
     * @param client
     */
    MergedResponse getAndInsertMergedData(ApiClient client);
}
