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
import cn.stylefeng.guns.modular.huobi.model.KlineDivide;
import cn.stylefeng.guns.modular.huobi.service.IKlineDivideService;

/**
 * k线细化划分控制器
 *
 * @author fengshuonan
 * @Date 2018-12-21 15:58:33
 */
@Controller
@RequestMapping("/klineDivide")
public class KlineDivideController extends BaseController {

    private String PREFIX = "/huobi/klineDivide/";

    @Autowired
    private IKlineDivideService klineDivideService;

    /**
     * 跳转到k线细化划分首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "klineDivide.html";
    }

    /**
     * 跳转到添加k线细化划分
     */
    @RequestMapping("/klineDivide_add")
    public String klineDivideAdd() {
        return PREFIX + "klineDivide_add.html";
    }

    /**
     * 跳转到修改k线细化划分
     */
    @RequestMapping("/klineDivide_update/{klineDivideId}")
    public String klineDivideUpdate(@PathVariable Integer klineDivideId, Model model) {
        KlineDivide klineDivide = klineDivideService.selectById(klineDivideId);
        model.addAttribute("item",klineDivide);
        LogObjectHolder.me().set(klineDivide);
        return PREFIX + "klineDivide_edit.html";
    }

    /**
     * 获取k线细化划分列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return klineDivideService.selectList(null);
    }

    /**
     * 新增k线细化划分
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(KlineDivide klineDivide) {
        klineDivideService.insert(klineDivide);
        return SUCCESS_TIP;
    }

    /**
     * 删除k线细化划分
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer klineDivideId) {
        klineDivideService.deleteById(klineDivideId);
        return SUCCESS_TIP;
    }

    /**
     * 修改k线细化划分
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(KlineDivide klineDivide) {
        klineDivideService.updateById(klineDivide);
        return SUCCESS_TIP;
    }

    /**
     * k线细化划分详情
     */
    @RequestMapping(value = "/detail/{klineDivideId}")
    @ResponseBody
    public Object detail(@PathVariable("klineDivideId") Integer klineDivideId) {
        return klineDivideService.selectById(klineDivideId);
    }
}
