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
import cn.stylefeng.guns.modular.huobi.model.Kline;
import cn.stylefeng.guns.modular.huobi.service.IKlineService;

/**
 * 单个symbol批量成交记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:26:29
 */
@Controller
@RequestMapping("/kline")
public class KlineController extends BaseController {

    private String PREFIX = "/huobi/kline/";

    @Autowired
    private IKlineService klineService;

    /**
     * 跳转到单个symbol批量成交记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "kline.html";
    }

    /**
     * 跳转到添加单个symbol批量成交记录
     */
    @RequestMapping("/kline_add")
    public String klineAdd() {
        return PREFIX + "kline_add.html";
    }

    /**
     * 跳转到修改单个symbol批量成交记录
     */
    @RequestMapping("/kline_update/{klineId}")
    public String klineUpdate(@PathVariable Integer klineId, Model model) {
        Kline kline = klineService.selectById(klineId);
        model.addAttribute("item",kline);
        LogObjectHolder.me().set(kline);
        return PREFIX + "kline_edit.html";
    }

    /**
     * 获取单个symbol批量成交记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return klineService.selectList(null);
    }

    /**
     * 新增单个symbol批量成交记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Kline kline) {
        klineService.insert(kline);
        return SUCCESS_TIP;
    }

    /**
     * 删除单个symbol批量成交记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer klineId) {
        klineService.deleteById(klineId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单个symbol批量成交记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Kline kline) {
        klineService.updateById(kline);
        return SUCCESS_TIP;
    }

    /**
     * 单个symbol批量成交记录详情
     */
    @RequestMapping(value = "/detail/{klineId}")
    @ResponseBody
    public Object detail(@PathVariable("klineId") Integer klineId) {
        return klineService.selectById(klineId);
    }
}
