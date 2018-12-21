package cn.stylefeng.guns.modular.huobi.dao;

import cn.stylefeng.guns.modular.huobi.model.Kline;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.google.common.collect.Lists;
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
    List<Integer> getLimitFrontIds(@Param("period") String period, @Param("size") Integer size,@Param("symbol") String symbol);


    List<Kline> selectInserted(@Param("period") String period, @Param("size") Integer size,@Param("symbol") String symbol);

    Kline selectLatest(@Param("period") String period,@Param("symbol") String symbol);

    int batchInsert(@Param("insertKlineList") List<Kline> insertKlineList);

    List<Kline> selectAllKline(@Param("period") String period,@Param("symbol") String symbol);
}
