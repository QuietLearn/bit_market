package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.MergedResponse;
import cn.stylefeng.guns.modular.huobi.model.Merged;
import cn.stylefeng.guns.modular.huobi.dao.MergedMapper;
import cn.stylefeng.guns.modular.huobi.service.IMergedService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@Service
public class MergedServiceImpl extends ServiceImpl<MergedMapper, Merged> implements IMergedService {
    private Logger logger = LoggerFactory.getLogger(MergedServiceImpl.class);

    @Autowired
    private MergedMapper mergedMapper;
    /**
     * 调用接口获取特定聚合行情(Ticker) 并插入到数据库
     * @param client
     */
    public MergedResponse getAndInsertMergedData(ApiClient client){
        MergedResponse mergedResponse = client.merged("btcusdt");
        Merged merged = (Merged) mergedResponse.getTick();
        merged.setTs(mergedResponse.getTs());

        Integer insertCount = mergedMapper.insert(merged);
        if (insertCount<=0)
            logger.error("插入merged 特定行情数据失败");
        return mergedResponse;
    }
}
