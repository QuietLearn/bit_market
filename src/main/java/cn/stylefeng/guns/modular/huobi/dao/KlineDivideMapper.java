package cn.stylefeng.guns.modular.huobi.dao;

import cn.stylefeng.guns.modular.huobi.model.KlineDivide;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyj
 * @since 2018-12-24
 */
public interface KlineDivideMapper extends BaseMapper<KlineDivide> {
    Integer insert(KlineDivide insertKlineDivide);

    KlineDivide selectLatest(String symbol);
}
