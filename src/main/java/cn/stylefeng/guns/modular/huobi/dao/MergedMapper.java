package cn.stylefeng.guns.modular.huobi.dao;

import cn.stylefeng.guns.modular.huobi.model.Merged;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
public interface MergedMapper extends BaseMapper<Merged> {

    @Override
    Integer insert(Merged merged);
}
