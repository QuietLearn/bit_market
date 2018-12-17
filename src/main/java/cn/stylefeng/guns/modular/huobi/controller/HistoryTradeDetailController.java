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
import cn.stylefeng.guns.modular.huobi.model.HistoryTradeDetail;
import cn.stylefeng.guns.modular.huobi.service.IHistoryTradeDetailService;

/**
 * 单个symbol批量成交记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:25:55
 */
@Controller
@RequestMapping("/historyTradeDetail")
public class HistoryTradeDetailController extends BaseController {

    private String PREFIX = "/huobi/historyTradeDetail/";

    @Autowired
    private IHistoryTradeDetailService historyTradeDetailService;

    /**
     * 跳转到单个symbol批量成交记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "historyTradeDetail.html";
    }

    /**
     * 跳转到添加单个symbol批量成交记录
     */
    @RequestMapping("/historyTradeDetail_add")
    public String historyTradeDetailAdd() {
        return PREFIX + "historyTradeDetail_add.html";
    }

    /**
     * 跳转到修改单个symbol批量成交记录
     */
    @RequestMapping("/historyTradeDetail_update/{historyTradeDetailId}")
    public String historyTradeDetailUpdate(@PathVariable Integer historyTradeDetailId, Model model) {
        HistoryTradeDetail historyTradeDetail = historyTradeDetailService.selectById(historyTradeDetailId);
        model.addAttribute("item",historyTradeDetail);
        LogObjectHolder.me().set(historyTradeDetail);
        return PREFIX + "historyTradeDetail_edit.html";
    }

    /**
     * 获取单个symbol批量成交记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return historyTradeDetailService.selectList(null);
    }

    /**
     * 新增单个symbol批量成交记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(HistoryTradeDetail historyTradeDetail) {
        historyTradeDetailService.insert(historyTradeDetail);
        return SUCCESS_TIP;
    }

    /**
     * 删除单个symbol批量成交记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer historyTradeDetailId) {
        historyTradeDetailService.deleteById(historyTradeDetailId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单个symbol批量成交记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(HistoryTradeDetail historyTradeDetail) {
        historyTradeDetailService.updateById(historyTradeDetail);
        return SUCCESS_TIP;
    }

    /**
     * 单个symbol批量成交记录详情
     */
    @RequestMapping(value = "/detail/{historyTradeDetailId}")
    @ResponseBody
    public Object detail(@PathVariable("historyTradeDetailId") Integer historyTradeDetailId) {
        return historyTradeDetailService.selectById(historyTradeDetailId);
    }
}
