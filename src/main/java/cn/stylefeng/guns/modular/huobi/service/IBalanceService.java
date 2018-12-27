package cn.stylefeng.guns.modular.huobi.service;

public interface IBalanceService {

    //获取创建的比特币下单id
    Long createOrderId(String amount,String price,String symbol,String type);
}
