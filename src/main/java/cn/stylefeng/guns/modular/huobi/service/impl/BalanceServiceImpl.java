package cn.stylefeng.guns.modular.huobi.service.impl;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.api.Main;
import cn.stylefeng.guns.huobi.mvc.dto.Accounts;
import cn.stylefeng.guns.huobi.request.CreateOrderRequest;
import cn.stylefeng.guns.huobi.response.AccountsResponse;
import cn.stylefeng.guns.huobi.response.BalanceResponse;
import cn.stylefeng.guns.modular.huobi.service.IBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceServiceImpl implements IBalanceService {

    @Autowired
    private Main main;

    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    public AccountsResponse getAndInsertAccountsData(ApiClient client){
        AccountsResponse accounts = client.accounts(true);
        return accounts;
    }


    public List<Accounts> getAccountList(){
        ApiClient apiClient = new ApiClient(main.API_KEY,main.API_SECRET);

        //------------------------------------------------------ accounts -------------------------------------------------------
        AccountsResponse accounts = getAndInsertAccountsData(apiClient);
        List<Accounts> list = (List<Accounts>) accounts.getData();
        return list;
    }

    public void getUserBalance(){
        ApiClient client = new ApiClient(main.API_KEY,main.API_SECRET);
        List<Accounts> accountList = getAccountList();
        if (!accountList.isEmpty()&&accountList.size()>0){
            BalanceResponse balance = client.balance(String.valueOf(accountList.get(0).getId()),true);
            if (accountList.size()>1){
                BalanceResponse balance2 = client.balance(String.valueOf(accountList.get(1).getId()),true);
            }
        }
    }


    public Long createOrderId(String amount,String price,String symbol,String type){
        ApiClient client = new ApiClient(main.API_KEY,main.API_SECRET);
        Long orderId = 111L;
        List<Accounts> list = getAccountList();
        if (!list.isEmpty()) {
            // find account id:
            Accounts account = list.get(0);
            long accountId = account.getId();
            // create order:
            CreateOrderRequest createOrderReq = new CreateOrderRequest();
            createOrderReq.accountId = String.valueOf(accountId);
            createOrderReq.amount = amount;
            createOrderReq.price = price;
            createOrderReq.symbol = symbol;

            createOrderReq.type = type;

            createOrderReq.source = "api";




            //------------------------------------------------------ 创建订单  -------------------------------------------------------
            orderId = client.createOrder(createOrderReq);
            // place order:

        }
        return orderId;
    }






}
