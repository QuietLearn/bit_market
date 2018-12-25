package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.response.MergedResponse;
import cn.stylefeng.guns.modular.huobi.dao.KlineDivideMapper;
import cn.stylefeng.guns.modular.huobi.model.KlineDivide;
import cn.stylefeng.guns.modular.huobi.model.Merged;
import cn.stylefeng.guns.modular.huobi.service.IKlineDivideService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        MergedResponse mergedResponse = client.merged(symbol);
        Merged merged = (Merged) mergedResponse.getTick();
        KlineDivide insertKlineDivide = new KlineDivide();
        BeanUtils.copyProperties(merged,insertKlineDivide);
        insertKlineDivide.setSymbol(symbol);
        insertKlineDivide.setKdTs(mergedResponse.getTs());
        insertKlineDivide.setGmtResponse(new Date(mergedResponse.getTs()));


        Integer insertCount = klineDivideMapper.insert(insertKlineDivide);
        if (insertCount<=0){
            logger.error("插入klineDivide失败");
        }
    }
}
