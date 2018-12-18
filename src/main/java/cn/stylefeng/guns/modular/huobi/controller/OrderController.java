package cn.stylefeng.guns.modular.huobi.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.huobi.model.Order;
import cn.stylefeng.guns.modular.huobi.service.IOrderService;

/**
 * 买卖盘控制器
 *
 * @author fengshuonan
 * @Date 2018-12-18 09:00:18
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private String PREFIX = "/huoibi/order/";

    @Autowired
    private IOrderService orderService;

    /**
     * 跳转到买卖盘首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "order.html";
    }

    /**
     * 跳转到添加买卖盘
     */
    @RequestMapping("/order_add")
    public String orderAdd() {
        return PREFIX + "order_add.html";
    }

    /**
     * 跳转到修改买卖盘
     */
    @RequestMapping("/order_update/{orderId}")
    public String orderUpdate(@PathVariable Integer orderId, Model model) {
        Order order = orderService.selectById(orderId);
        model.addAttribute("item",order);
        LogObjectHolder.me().set(order);
        return PREFIX + "order_edit.html";
    }

    /**
     * 获取买卖盘列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return orderService.selectList(null);
    }

    /**
     * 新增买卖盘
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Order order) {
        orderService.insert(order);
        return SUCCESS_TIP;
    }

    /**
     * 删除买卖盘
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer orderId) {
        orderService.deleteById(orderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改买卖盘
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Order order) {
        orderService.updateById(order);
        return SUCCESS_TIP;
    }

    /**
     * 买卖盘详情
     */
    @RequestMapping(value = "/detail/{orderId}")
    @ResponseBody
    public Object detail(@PathVariable("orderId") Integer orderId) {
        return orderService.selectById(orderId);
    }
}
