package cn.stylefeng.guns.huobi.api;

import cn.stylefeng.guns.huobi.mvc.dto.Accounts;
import cn.stylefeng.guns.huobi.request.DepthRequest;
import cn.stylefeng.guns.huobi.response.*;
import cn.stylefeng.guns.huobi.response.KlineResponse;
import cn.stylefeng.guns.huobi.response.MergedResponse;
import cn.stylefeng.guns.huobi.util.JsonUtil;
import cn.stylefeng.guns.modular.huobi.dao.*;
import cn.stylefeng.guns.modular.huobi.model.*;
import cn.stylefeng.guns.modular.huobi.service.*;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    static final String API_KEY = "2f9b2ebe-220ff18c-5d7d8af4-6729a";
    static final String API_SECRET = "7466c739-a43ff7bb-d228a9e6-0aa09";

    @Autowired
    private IKlineService klineService;
    @Autowired
    private KlineMapper klineMapper;
    @Autowired
    private IMergedService mergedService;
    @Autowired
    private ITickersService tickersService;
    @Autowired
    private DepthMapper depthMapper;
    @Autowired
    private ITradeDetailService tradeDetailService;
    @Autowired
    private TradeDetailMapper tradeDetailMapper;
    @Autowired
    private IHistoryTradeDetailService historyTradeDetailService;
    @Autowired
    private IMarketDetailService marketDetailService;
    @Autowired
    private ISymbolsService symbolsService;

    @Autowired
    private IOrderService orderService;
    public static void main(String[] args) {
        try {
            new Main().apiSample();
        } catch (ApiException e) {
            System.err.println("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private cn.stylefeng.guns.modular.huobi.model.Tickers assemTickers(Tickers tickers){
        cn.stylefeng.guns.modular.huobi.model.Tickers dbTickers = new cn.stylefeng.guns.modular.huobi.model.Tickers();
        BeanUtils.copyProperties(tickers,dbTickers);
        return dbTickers;
    }


    /**
     * 调用接口获取 全部symbol的交易行情 并插入数据库
     * @param client
     */
    public void getAndInsertTickersData(ApiClient client){
        TickersResponse tickersResponse = client.tickers();
        List<Tickers> tickersList = (List<Tickers>) tickersResponse.getData();
        //List<cn.stylefeng.guns.modular.huobi.model.Tickers> dbTickersList = Lists.newArrayList();
        //dbTickersList.add(dbTickers);
        /*for (Tickers tickers : tickersList) {
            tickers.setTs(tickersResponse.getTs());
            //cn.stylefeng.guns.modular.huobi.model.Tickers dbTickers = assemTickers(tickers);
        }*/
        print(tickersResponse);
        tickersService.insertBatch(tickersList);
    }

    /**
     * 调用接口获取 市场深度行情（单个特定symbol） 并插入数据库
     * @param client
     */
    public void getAndInsertDepthData(ApiClient client){
        DepthRequest depthRequest = new DepthRequest();
        depthRequest.setSymbol("btcusdt");
        depthRequest.setType("step0");
        DepthResponse depth = client.depth(depthRequest);

        Depth tick = depth.getTick();

        List<List<BigDecimal>> bids = tick.getBids();

        List<List<BigDecimal>> asks = tick.getAsks();

        List<Order> insertOrderList = Lists.newArrayList();
        for (List<BigDecimal> bid:bids) {
            Order order = new Order();
            order.setPrice(bid.get(0));
            order.setAmount(bid.get(1));
            order.setType(0);
            insertOrderList.add(order);
        }

        for (List<BigDecimal> ask:asks) {
            Order order = new Order();
            order.setPrice(ask.get(0));
            order.setAmount(ask.get(1));
            order.setType(1);
            insertOrderList.add(order);
        }

        //获取买盘数据
        List<Order> existOrderList = orderService.selectList(null);

        if (CollectionUtils.isNotEmpty(insertOrderList)) {
            insertOrderList.removeAll(existOrderList);
        }
        if (CollectionUtils.isNotEmpty(insertOrderList)) {
            boolean b = orderService.insertBatch(insertOrderList);
        }

        }
       /* List<Depth> depthList = depthMapper.selectAll();
        if (CollectionUtils.isNotEmpty(depthList)&&depthList.contains(tick)){
            logger.info("数据库中已存在此深度行情depth");
            return;
        }*/

        
    }
    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    public void getAndInsertTradeData(ApiClient client){
        TradeResponse tradeResponse = client.trade("ethusdt");

        //获取返回响应的json对象的 trade data
        Trade trade = tradeResponse.getTick();
        //获取data里的data
        TradeDetail tradeDetail = (TradeDetail) trade.getData();
        //两个id不同bargainId是成交id(里面的),id消息id，外面的
        tradeDetail.setBargainId(tradeDetail.getBargainId());
        tradeDetail.setId(trade.getId());

        List<Long> existIds = tradeDetailMapper.getAllIds();

        if (existIds.contains(tradeDetail.getBargainId())){
            logger.error("HistoryTrade的成交id已存在于数据库中");
            return;
        }

        print(trade);
        tradeDetailService.insert(tradeDetail);
    }

    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    public void getAndInsertHistoryTradeData(ApiClient client){
        HistoryTradeResponse historyTrade = client.historyTrade("ethusdt", "20");
        //获取返回响应的json对象的 historyTrade data
        List<Trade> historyTradeResponseList = (List<Trade>) historyTrade.getData();
        //获取data里的data
        List<TradeDetail> tradeDetailList = Lists.newArrayList();
        List<Long> insertIds = Lists.newArrayList();
        for (Trade historyTradeResponse:historyTradeResponseList) {
            TradeDetail historyTradeDetail = (TradeDetail) historyTradeResponse.getData();
            //两个id不同bargainId是成交id(里面的),id消息id，外面的
            insertIds.add(historyTradeDetail.getBargainId());
            historyTradeDetail.setBargainId(historyTradeDetail.getBargainId());
            historyTradeDetail.setId(historyTradeResponse.getId());
            tradeDetailList.add(historyTradeDetail);
        }

        List<Long> existIds = tradeDetailMapper.getAllIds();

        if (existIds.retainAll(insertIds)){
            logger.error("HistoryTrade的成交id已存在于数据库中");
            return;
        }
        print(historyTrade);
        tradeDetailService.insertBatch(tradeDetailList);
    }
    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    public void getAndInsertDetailData(ApiClient client){
        DetailResponse detailTrade = client.detail("ethusdt");
        print(detailTrade);
        MarketDetail marketDetail = (MarketDetail) detailTrade.getTick();
        marketDetailService.insert(marketDetail);
    }
    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    public void getAndInsertSymbolsData(ApiClient client){
        SymbolsResponse symbols = client.symbols(false);
        List<Symbols> sysbomlsList = (List<Symbols>) symbols.getData();

        print(symbols);
        symbolsService.insertBatch(sysbomlsList);
    }

    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    public void getAndInsertCurrencysData(ApiClient client){
        CurrencysResponse currencys = client.currencys(false);
        print(currencys);
    }
    /**
     * 调用接口获取  并插入数据库
     * @param client
     */
    public AccountsResponse getAndInsertAccountsData(ApiClient client){
        AccountsResponse accounts = client.accounts(false);
        print(accounts);
        return accounts;
    }


    public void apiSample() {
        // create ApiClient using your api key and api secret:
        ApiClient client = new ApiClient(API_KEY, API_SECRET);
        // get symbol list:
        //print(client.getSymbols());

        //获取 K 线
        //------------------------------------------------------ kline -------------------------------------------------------
        KlineResponse klineResponse = klineService.getAndInsertKlineData(client);
        print(klineResponse);
        //------------------------------------------------------ merged -------------------------------------------------------
        MergedResponse mergedResponse = mergedService.getAndInsertMergedData(client);
        print(mergedResponse);

        //------------------------------------------------------ tickers -------------------------------------------------------
        getAndInsertTickersData(client);

        //------------------------------------------------------ depth -------------------------------------------------------
        getAndInsertDepthData(client);
        //------------------------------------------------------ trade -------------------------------------------------------
        getAndInsertTradeData(client);

        //------------------------------------------------------ historyTrade -------------------------------------------------------
        getAndInsertHistoryTradeData(client);

        //------------------------------------------------------ datail -------------------------------------------------------
        getAndInsertDetailData(client);

        //------------------------------------------------------ symbols -------------------------------------------------------
        getAndInsertSymbolsData(client);

        //------------------------------------------------------ Currencys -------------------------------------------------------
        getAndInsertCurrencysData(client);

        //------------------------------------------------------ timestamp -------------------------------------------------------
        TimestampResponse timestamp = client.timestamp(false);
        print(timestamp);

        //------------------------------------------------------ accounts -------------------------------------------------------
        AccountsResponse accounts = getAndInsertAccountsData(client);

        //------------------------------------------------------ balance -------------------------------------------------------
        List<Accounts> list = (List<Accounts>) accounts.getData();
        if (!list.isEmpty()&&list.size()>0){
            BalanceResponse balance = client.balance(String.valueOf(list.get(0).getId()),false);
            print(balance); //spot
            if (list.size()>1){
                BalanceResponse balance2 = client.balance(String.valueOf(list.get(1).getId()),false);
                print(balance2);//otc
            }
        }

        /*Long orderId = 123L;
        if (!list.isEmpty()) {
            // find account id:
            Accounts account = list.get(0);
            long accountId = account.getId();
            // create order:
            CreateOrderRequest createOrderReq = new CreateOrderRequest();
            createOrderReq.accountId = String.valueOf(accountId);
            createOrderReq.amount = "0.02";
            createOrderReq.price = "0.1";
            createOrderReq.symbol = "eosusdt";
            createOrderReq.type = CreateOrderRequest.OrderType.BUY_LIMIT;
            createOrderReq.source = "api";

            //------------------------------------------------------ 创建订单  -------------------------------------------------------
            orderId = client.createOrder(createOrderReq);
            print(orderId);
            // place order:

            
        }

        //------------------------------------------------------ submitcancel 取消订单 -------------------------------------------------------

//    SubmitcancelResponse submitcancel = client.submitcancel(orderId.toString());
//    print(submitcancel);

        //------------------------------------------------------ submitcancel 批量取消订单-------------------------------------------------------
//    String[] orderList = {"727554767","727554766",""};
//    String[] orderList = {String.valueOf(orderId)};
        List orderList = new ArrayList();
        orderList.add(orderId);
        BatchcancelResponse submitcancels = client.submitcancels(orderList);
        print(submitcancels);

        //------------------------------------------------------ ordersDetail 订单详情 -------------------------------------------------------
        OrdersDetailResponse ordersDetail = client.ordersDetail(String.valueOf(orderId));
        print(ordersDetail);

        //------------------------------------------------------ ordersDetail 已经成交的订单详情 -------------------------------------------------------
//    String.valueOf(orderId)
        MatchresultsOrdersDetailResponse matchresults = client.matchresults("714746923");
        print(ordersDetail);

        //------------------------------------------------------ ordersDetail 已经成交的订单详情 -------------------------------------------------------
//    String.valueOf(orderId)
        IntrustOrdersDetailRequest req = new IntrustOrdersDetailRequest();
        req.symbol = "btcusdt";
        req.types = IntrustOrdersDetailRequest.OrderType.BUY_LIMIT;
//    req.startDate = "2018-01-01";
//    req.endDate = "2018-01-14";
        req.states = IntrustOrdersDetailRequest.OrderStates.FILLED;*/
//    req.from = "";
//    req.direct = "";
//    req.size = "";


//    public String symbol;	   //true	string	交易对		btcusdt, bccbtc, rcneth ...
//    public String types;	   //false	string	查询的订单类型组合，使用','分割		buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖
//    public String startDate;   //false	string	查询开始日期, 日期格式yyyy-mm-dd
//    public String endDate;	   //false	string	查询结束日期, 日期格式yyyy-mm-dd
//    public String states;	   //true	string	查询的订单状态组合，使用','分割		pre-submitted 准备提交, submitted 已提交, partial-filled 部分成交,
//    // partial-canceled 部分成交撤销, filled 完全成交, canceled 已撤销
//    public String from;	       //false	string	查询起始 ID
//    public String direct;	   //false	string	查询方向		prev 向前，next 向后
//    public String size;	       //false	string	查询记录大小


        //------------------------------------------------------ order 查询当前委托、历史委托 -------------------------------------------------------

        /*IntrustDetailResponse intrustDetail = client.intrustOrdersDetail(req);
        print(intrustDetail);*/


//    // get accounts:
//    List<Account> accounts1 = client.getAccounts();
//    print(accounts1);

    }

    static void print(Object obj) {
        try {
            System.out.println(JsonUtil.writeValue(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
