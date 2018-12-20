package cn.stylefeng.guns.modular.huobi.dao;

import cn.stylefeng.guns.modular.huobi.model.Kline;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
public interface KlineMapper extends BaseMapper<Kline> {
    List<Integer> getAllIds(@Param("period") String period, @Param("size") Integer size);


    List<Kline> selectInserted(@Param("period") String period, @Param("size") Integer size);

    int batchInsert(@Param("insertKlineList") List<Kline> insertKlineList);
}
