package cn.stylefeng.guns.modular.huobi.service;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.DepthResponse;
import cn.stylefeng.guns.modular.huobi.model.Depth;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
public interface IDepthService extends IService<Depth> {
    /**
     * 调用接口获取 市场深度行情（单个特定symbol） 并插入数据库
     * @param client
     */
    public DepthResponse getAndInsertDepthData(ApiClient client);
}
