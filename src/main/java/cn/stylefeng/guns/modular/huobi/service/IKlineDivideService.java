package cn.stylefeng.guns.modular.huobi.service;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.modular.huobi.model.KlineDivide;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyj
 * @since 2018-12-21
 */
public interface IKlineDivideService extends IService<KlineDivide> {
    void getAndInsertKlineDivideData(String symbol,  ApiClient client);
}
