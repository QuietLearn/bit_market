package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.modular.huobi.model.MarketDetail;
import cn.stylefeng.guns.modular.huobi.dao.MarketDetailMapper;
import cn.stylefeng.guns.modular.huobi.service.IMarketDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 滚动24小时交易聚合行情(单个symbol) 服务实现类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@Service
public class MarketDetailServiceImpl extends ServiceImpl<MarketDetailMapper, MarketDetail> implements IMarketDetailService {

}
