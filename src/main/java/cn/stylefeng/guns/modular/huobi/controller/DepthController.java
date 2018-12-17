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
import cn.stylefeng.guns.modular.huobi.model.Depth;
import cn.stylefeng.guns.modular.huobi.service.IDepthService;

/**
 * 市场深度行情& #40;单symbol& #41;控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 17:19:27
 */
@Controller
@RequestMapping("/depth")
public class DepthController extends BaseController {

    private String PREFIX = "/huobi/depth/";

    @Autowired
    private IDepthService depthService;

    /**
     * 跳转到市场深度行情& #40;单symbol& #41;首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "depth.html";
    }

    /**
     * 跳转到添加市场深度行情& #40;单symbol& #41;
     */
    @RequestMapping("/depth_add")
    public String depthAdd() {
        return PREFIX + "depth_add.html";
    }

    /**
     * 跳转到修改市场深度行情& #40;单symbol& #41;
     */
    @RequestMapping("/depth_update/{depthId}")
    public String depthUpdate(@PathVariable Integer depthId, Model model) {
        Depth depth = depthService.selectById(depthId);
        model.addAttribute("item",depth);
        LogObjectHolder.me().set(depth);
        return PREFIX + "depth_edit.html";
    }

    /**
     * 获取市场深度行情& #40;单symbol& #41;列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return depthService.selectList(null);
    }

    /**
     * 新增市场深度行情& #40;单symbol& #41;
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Depth depth) {
        depthService.insert(depth);
        return SUCCESS_TIP;
    }

    /**
     * 删除市场深度行情& #40;单symbol& #41;
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer depthId) {
        depthService.deleteById(depthId);
        return SUCCESS_TIP;
    }

    /**
     * 修改市场深度行情& #40;单symbol& #41;
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Depth depth) {
        depthService.updateById(depth);
        return SUCCESS_TIP;
    }

    /**
     * 市场深度行情& #40;单symbol& #41;详情
     */
    @RequestMapping(value = "/detail/{depthId}")
    @ResponseBody
    public Object detail(@PathVariable("depthId") Integer depthId) {
        return depthService.selectById(depthId);
    }
}
