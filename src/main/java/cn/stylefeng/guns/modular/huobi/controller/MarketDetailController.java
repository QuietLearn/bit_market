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
import cn.stylefeng.guns.modular.huobi.model.MarketDetail;
import cn.stylefeng.guns.modular.huobi.service.IMarketDetailService;

/**
 * 单个symbol批量成交记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:27:18
 */
@Controller
@RequestMapping("/marketDetail")
public class MarketDetailController extends BaseController {

    private String PREFIX = "/huobi/marketDetail/";

    @Autowired
    private IMarketDetailService marketDetailService;

    /**
     * 跳转到单个symbol批量成交记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "marketDetail.html";
    }

    /**
     * 跳转到添加单个symbol批量成交记录
     */
    @RequestMapping("/marketDetail_add")
    public String marketDetailAdd() {
        return PREFIX + "marketDetail_add.html";
    }

    /**
     * 跳转到修改单个symbol批量成交记录
     */
    @RequestMapping("/marketDetail_update/{marketDetailId}")
    public String marketDetailUpdate(@PathVariable Integer marketDetailId, Model model) {
        MarketDetail marketDetail = marketDetailService.selectById(marketDetailId);
        model.addAttribute("item",marketDetail);
        LogObjectHolder.me().set(marketDetail);
        return PREFIX + "marketDetail_edit.html";
    }

    /**
     * 获取单个symbol批量成交记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return marketDetailService.selectList(null);
    }

    /**
     * 新增单个symbol批量成交记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MarketDetail marketDetail) {
        marketDetailService.insert(marketDetail);
        return SUCCESS_TIP;
    }

    /**
     * 删除单个symbol批量成交记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer marketDetailId) {
        marketDetailService.deleteById(marketDetailId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单个symbol批量成交记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MarketDetail marketDetail) {
        marketDetailService.updateById(marketDetail);
        return SUCCESS_TIP;
    }

    /**
     * 单个symbol批量成交记录详情
     */
    @RequestMapping(value = "/detail/{marketDetailId}")
    @ResponseBody
    public Object detail(@PathVariable("marketDetailId") Integer marketDetailId) {
        return marketDetailService.selectById(marketDetailId);
    }
}
