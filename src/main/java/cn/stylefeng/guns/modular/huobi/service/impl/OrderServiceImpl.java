package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.modular.huobi.model.Order;
import cn.stylefeng.guns.modular.huobi.dao.OrderMapper;
import cn.stylefeng.guns.modular.huobi.service.IOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyj
 * @since 2018-12-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
