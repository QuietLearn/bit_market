package cn.stylefeng.guns.huobi.mvc.controller;

import cn.stylefeng.guns.huobi.api.ApiException;
import cn.stylefeng.guns.huobi.api.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/huobi")
@RestController
public class HuobiDataController {

    static final String API_KEY = "2f9b2ebe-220ff18c-5d7d8af4-6729a";
    static final String API_SECRET = "7466c739-a43ff7bb-d228a9e6-0aa09";

    @Autowired
    private Main main;

    @RequestMapping("/getBitusdt")
    public void getBitusdtData(){
        try {
            main.apiSample();
        } catch (ApiException e) {
            System.err.println("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
