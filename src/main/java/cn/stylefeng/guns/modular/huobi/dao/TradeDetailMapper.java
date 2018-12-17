package cn.stylefeng.guns.modular.huobi.dao;

import cn.stylefeng.guns.modular.huobi.model.TradeDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
public interface TradeDetailMapper extends BaseMapper<TradeDetail> {
    List<Long> getAllIds();
}
