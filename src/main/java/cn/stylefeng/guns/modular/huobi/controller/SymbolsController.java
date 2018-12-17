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
import cn.stylefeng.guns.modular.huobi.model.Symbols;
import cn.stylefeng.guns.modular.huobi.service.ISymbolsService;

/**
 * 单个symbol批量成交记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:28:31
 */
@Controller
@RequestMapping("/symbols")
public class SymbolsController extends BaseController {

    private String PREFIX = "/huobi/symbols/";

    @Autowired
    private ISymbolsService symbolsService;

    /**
     * 跳转到单个symbol批量成交记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "symbols.html";
    }

    /**
     * 跳转到添加单个symbol批量成交记录
     */
    @RequestMapping("/symbols_add")
    public String symbolsAdd() {
        return PREFIX + "symbols_add.html";
    }

    /**
     * 跳转到修改单个symbol批量成交记录
     */
    @RequestMapping("/symbols_update/{symbolsId}")
    public String symbolsUpdate(@PathVariable Integer symbolsId, Model model) {
        Symbols symbols = symbolsService.selectById(symbolsId);
        model.addAttribute("item",symbols);
        LogObjectHolder.me().set(symbols);
        return PREFIX + "symbols_edit.html";
    }

    /**
     * 获取单个symbol批量成交记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return symbolsService.selectList(null);
    }

    /**
     * 新增单个symbol批量成交记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Symbols symbols) {
        symbolsService.insert(symbols);
        return SUCCESS_TIP;
    }

    /**
     * 删除单个symbol批量成交记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer symbolsId) {
        symbolsService.deleteById(symbolsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单个symbol批量成交记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Symbols symbols) {
        symbolsService.updateById(symbols);
        return SUCCESS_TIP;
    }

    /**
     * 单个symbol批量成交记录详情
     */
    @RequestMapping(value = "/detail/{symbolsId}")
    @ResponseBody
    public Object detail(@PathVariable("symbolsId") Integer symbolsId) {
        return symbolsService.selectById(symbolsId);
    }
}
