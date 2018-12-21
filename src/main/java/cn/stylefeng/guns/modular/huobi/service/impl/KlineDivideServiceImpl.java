package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.KlineResponse;
import cn.stylefeng.guns.modular.huobi.model.Kline;
import cn.stylefeng.guns.modular.huobi.model.KlineDivide;
import cn.stylefeng.guns.modular.huobi.dao.KlineDivideMapper;
import cn.stylefeng.guns.modular.huobi.service.IKlineDivideService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyj
 * @since 2018-12-21
 */
@Service
public class KlineDivideServiceImpl extends ServiceImpl<KlineDivideMapper, KlineDivide> implements IKlineDivideService {

    private static final Logger logger = LoggerFactory.getLogger(KlineDivideServiceImpl.class);
    @Autowired
    private KlineDivideMapper klineDivideMapper;

    public void getAndInsertKlineDivideData(String symbol,  ApiClient client){
        KlineResponse klineResponse = client.kline(symbol, "1min", "1");
        List<Kline> insertKlineList = (List<Kline>) klineResponse.getData();
        Kline kline = insertKlineList.get(0);
        KlineDivide insertKlineDivide = new KlineDivide();
        BeanUtils.copyProperties(kline,insertKlineDivide);
        insertKlineDivide.setTs(Long.valueOf(klineResponse.getTs()));
        insertKlineDivide.setGmtResponse(new Date(Long.valueOf(klineResponse.getTs())));
        insertKlineDivide.setSymbol(symbol);



        Integer insertCount = klineDivideMapper.insert(insertKlineDivide);
        if (insertCount<=0){
            logger.error("插入klineDivide失败");
        }
    }
}
