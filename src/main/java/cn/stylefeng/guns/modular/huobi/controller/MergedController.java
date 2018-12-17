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
import cn.stylefeng.guns.modular.huobi.model.Merged;
import cn.stylefeng.guns.modular.huobi.service.IMergedService;

/**
 * 单个symbol批量成交记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:27:50
 */
@Controller
@RequestMapping("/merged")
public class MergedController extends BaseController {

    private String PREFIX = "/huobi/merged/";

    @Autowired
    private IMergedService mergedService;

    /**
     * 跳转到单个symbol批量成交记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "merged.html";
    }

    /**
     * 跳转到添加单个symbol批量成交记录
     */
    @RequestMapping("/merged_add")
    public String mergedAdd() {
        return PREFIX + "merged_add.html";
    }

    /**
     * 跳转到修改单个symbol批量成交记录
     */
    @RequestMapping("/merged_update/{mergedId}")
    public String mergedUpdate(@PathVariable Integer mergedId, Model model) {
        Merged merged = mergedService.selectById(mergedId);
        model.addAttribute("item",merged);
        LogObjectHolder.me().set(merged);
        return PREFIX + "merged_edit.html";
    }

    /**
     * 获取单个symbol批量成交记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return mergedService.selectList(null);
    }

    /**
     * 新增单个symbol批量成交记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Merged merged) {
        mergedService.insert(merged);
        return SUCCESS_TIP;
    }

    /**
     * 删除单个symbol批量成交记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer mergedId) {
        mergedService.deleteById(mergedId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单个symbol批量成交记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Merged merged) {
        mergedService.updateById(merged);
        return SUCCESS_TIP;
    }

    /**
     * 单个symbol批量成交记录详情
     */
    @RequestMapping(value = "/detail/{mergedId}")
    @ResponseBody
    public Object detail(@PathVariable("mergedId") Integer mergedId) {
        return mergedService.selectById(mergedId);
    }
}
