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
import cn.stylefeng.guns.modular.huobi.model.Tickers;
import cn.stylefeng.guns.modular.huobi.service.ITickersService;

/**
 * 单个symbol批量成交记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:28:40
 */
@Controller
@RequestMapping("/tickers")
public class TickersController extends BaseController {

    private String PREFIX = "/huobi/tickers/";

    @Autowired
    private ITickersService tickersService;

    /**
     * 跳转到单个symbol批量成交记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tickers.html";
    }

    /**
     * 跳转到添加单个symbol批量成交记录
     */
    @RequestMapping("/tickers_add")
    public String tickersAdd() {
        return PREFIX + "tickers_add.html";
    }

    /**
     * 跳转到修改单个symbol批量成交记录
     */
    @RequestMapping("/tickers_update/{tickersId}")
    public String tickersUpdate(@PathVariable Integer tickersId, Model model) {
        Tickers tickers = tickersService.selectById(tickersId);
        model.addAttribute("item",tickers);
        LogObjectHolder.me().set(tickers);
        return PREFIX + "tickers_edit.html";
    }

    /**
     * 获取单个symbol批量成交记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return tickersService.selectList(null);
    }

    /**
     * 新增单个symbol批量成交记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Tickers tickers) {
        tickersService.insert(tickers);
        return SUCCESS_TIP;
    }

    /**
     * 删除单个symbol批量成交记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tickersId) {
        tickersService.deleteById(tickersId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单个symbol批量成交记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Tickers tickers) {
        tickersService.updateById(tickers);
        return SUCCESS_TIP;
    }

    /**
     * 单个symbol批量成交记录详情
     */
    @RequestMapping(value = "/detail/{tickersId}")
    @ResponseBody
    public Object detail(@PathVariable("tickersId") Integer tickersId) {
        return tickersService.selectById(tickersId);
    }
}
