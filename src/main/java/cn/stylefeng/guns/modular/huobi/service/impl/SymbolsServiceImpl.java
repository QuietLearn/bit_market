package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.modular.huobi.model.Symbols;
import cn.stylefeng.guns.modular.huobi.dao.SymbolsMapper;
import cn.stylefeng.guns.modular.huobi.service.ISymbolsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Pro站支持的所有交易对及精度 服务实现类
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@Service
public class SymbolsServiceImpl extends ServiceImpl<SymbolsMapper, Symbols> implements ISymbolsService {

}
