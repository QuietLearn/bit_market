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
import cn.stylefeng.guns.modular.huobi.model.TradeDetail;
import cn.stylefeng.guns.modular.huobi.service.ITradeDetailService;

/**
 * 单个symbol批量成交记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:28:45
 */
@Controller
@RequestMapping("/tradeDetail")
public class TradeDetailController extends BaseController {

    private String PREFIX = "/huobi/tradeDetail/";

    @Autowired
    private ITradeDetailService tradeDetailService;

    /**
     * 跳转到单个symbol批量成交记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tradeDetail.html";
    }

    /**
     * 跳转到添加单个symbol批量成交记录
     */
    @RequestMapping("/tradeDetail_add")
    public String tradeDetailAdd() {
        return PREFIX + "tradeDetail_add.html";
    }

    /**
     * 跳转到修改单个symbol批量成交记录
     */
    @RequestMapping("/tradeDetail_update/{tradeDetailId}")
    public String tradeDetailUpdate(@PathVariable Integer tradeDetailId, Model model) {
        TradeDetail tradeDetail = tradeDetailService.selectById(tradeDetailId);
        model.addAttribute("item",tradeDetail);
        LogObjectHolder.me().set(tradeDetail);
        return PREFIX + "tradeDetail_edit.html";
    }

    /**
     * 获取单个symbol批量成交记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return tradeDetailService.selectList(null);
    }

    /**
     * 新增单个symbol批量成交记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TradeDetail tradeDetail) {
        tradeDetailService.insert(tradeDetail);
        return SUCCESS_TIP;
    }

    /**
     * 删除单个symbol批量成交记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tradeDetailId) {
        tradeDetailService.deleteById(tradeDetailId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单个symbol批量成交记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TradeDetail tradeDetail) {
        tradeDetailService.updateById(tradeDetail);
        return SUCCESS_TIP;
    }

    /**
     * 单个symbol批量成交记录详情
     */
    @RequestMapping(value = "/detail/{tradeDetailId}")
    @ResponseBody
    public Object detail(@PathVariable("tradeDetailId") Integer tradeDetailId) {
        return tradeDetailService.selectById(tradeDetailId);
    }
}
