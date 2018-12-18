package cn.stylefeng.guns.huobi.api;

import cn.stylefeng.guns.huobi.mvc.dto.Accounts;
import cn.stylefeng.guns.huobi.mvc.dto.Balance;
import cn.stylefeng.guns.huobi.mvc.dto.BatchcancelBean;
import cn.stylefeng.guns.huobi.mvc.dto.Details;
import cn.stylefeng.guns.modular.huobi.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import cn.stylefeng.guns.huobi.request.CreateOrderRequest;
import cn.stylefeng.guns.huobi.request.DepthRequest;
import cn.stylefeng.guns.huobi.request.IntrustOrdersDetailRequest;
import cn.stylefeng.guns.huobi.response.*;
import cn.stylefeng.guns.huobi.signature.ApiSignature;
import cn.stylefeng.guns.huobi.util.JsonUtil;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * API client.
 *
 * @Date 2018/1/14
 * @Time 16:02
 */
public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    static final int CONN_TIMEOUT = 5;
    static final int READ_TIMEOUT = 5;
    static final int WRITE_TIMEOUT = 5;


    static final String API_URL = "https://api.huobi.pro";
    static final String API_HOST = getHost();
    
    static final MediaType JSON = MediaType.parse("application/json");
    static OkHttpClient client = getClient();

    final String accessKeyId;
    final String accessKeySecret;
    final String assetPassword;

    /**
     * 创建一个ApiClient实例
     *
     * @param accessKeyId     AccessKeyId
     * @param accessKeySecret AccessKeySecret
     */
    public ApiClient(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.assetPassword = null;
    }

    /**
     * 创建一个ApiClient实例
     *
     * @param accessKeyId     AccessKeyId
     * @param accessKeySecret AccessKeySecret
     * @param assetPassword   AssetPassword
     */
    public ApiClient(String accessKeyId, String accessKeySecret, String assetPassword) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.assetPassword = assetPassword;
    }

    /**
     * 查询交易对
     *
     * @return List of symbols.
     */
    public List<Symbols> getSymbols(boolean needSignature) {
        ApiResponse<List<Symbols>> resp =
                get("/v1/common/symbols", null, new TypeReference<ApiResponse<List<Symbols>>>() {
                },needSignature);
        return resp.checkAndReturn();
    }

    /**
     * 查询所有账户信息
     *
     * @return List of accounts.
     */
    public List<Account> getAccounts(boolean needSignature) {
        ApiResponse<List<Account>> resp =
                get("/v1/account/accounts", null, new TypeReference<ApiResponse<List<Account>>>() {
                },needSignature);
        return resp.checkAndReturn();
    }

    /**
     * 创建订单
     *
     * @param request CreateOrderRequest object.
     * @return Order id.
     */
    public Long createOrder(CreateOrderRequest request) {
//        ApiResponse<Long> resp =
//                post("/v1/order/orders", request, new TypeReference<ApiResponse<Long>>() {
//                });
        ApiResponse<Long> resp =
                        post("/v1/order/orders/place", request, new TypeReference<ApiResponse<Long>>() {
                        });
        return resp.checkAndReturn();
    }

    /**
     * 执行订单
     *
     * @param orderId The id of created order.
     * @return Order id.
     */
    public String placeOrder(long orderId) {
        ApiResponse<String> resp = post("/v1/order/orders/" + orderId + "/place", null,
                new TypeReference<ApiResponse<String>>() {
                });
        return resp.checkAndReturn();
    }


    // ----------------------------------------行情API-------------------------------------------

    /**
     * GET /market/history/kline 获取K线数据
     *
     * @param symbol
     * @param period
     * @param size
     * @return
     */
    public KlineResponse kline(String symbol, String period, String size) {
        HashMap map = new HashMap();
        map.put("symbol", symbol);
        map.put("period", period);
        map.put("size", size);
        KlineResponse resp = get("/market/history/kline", map, new TypeReference<KlineResponse<List<Kline>>>() {
        });
        return resp;
    }

    /**
     * GET /market/detail/merged 获取聚合行情(Ticker)
     *
     * @param symbol
     * @return
     */
    public MergedResponse merged(String symbol) {
        HashMap map = new HashMap();
        map.put("symbol", symbol);
        MergedResponse resp = get("/market/detail/merged", map, new TypeReference<MergedResponse<Merged>>() {
        });
        return resp;
    }

    /**
     * GET /market/tickers 全部symbol的交易行情
     *
     * @return
     */
    public TickersResponse tickers() {
        TickersResponse resp = get("/market/tickers", null, new TypeReference<TickersResponse<List<Tickers>>>() {
        });
        return resp;
    }


    /**
     * GET /market/depth 获取 Market Depth 数据
     *
     * @param request
     * @return
     */
    public DepthResponse depth(DepthRequest request) {
        HashMap map = new HashMap();
        map.put("symbol", request.getSymbol());
        map.put("type", request.getType());

        DepthResponse resp = get("/market/depth", map, new TypeReference<DepthResponse<Depth>>() {
        });
        return resp;
    }

    /**
     * GET /market/trade 获取 Trade Detail 数据
     *
     * @param symbol
     * @return
     */
    public TradeResponse trade(String symbol) {
        HashMap map = new HashMap();
        map.put("symbol", symbol);
        TradeResponse resp = get("/market/trade", map, new TypeReference<TradeResponse>() {
        });
        return resp;
    }

    /**
     * GET /market/history/trade 批量获取最近的交易记录
     *
     * @param symbol
     * @param size
     * @return
     */
    public HistoryTradeResponse historyTrade(String symbol, String size) {
        HashMap map = new HashMap();
        map.put("symbol", symbol);
        map.put("size", size);
        HistoryTradeResponse resp = get("/market/history/trade", map, new TypeReference<HistoryTradeResponse>() {
        });
        return resp;
    }

    /**
     * GET /market/detail 获取 Market Detail 24小时成交量数据
     *
     * @param symbol
     * @return
     */
    public DetailResponse detail(String symbol) {
        HashMap map = new HashMap();
        map.put("symbol", symbol);
        DetailResponse resp = get("/market/detail", map, new TypeReference<DetailResponse<Details>>() {
        });
        return resp;
    }


    /**
     * GET /v1/common/symbols 查询系统支持的所有交易对及精度
     *
     * @param symbol
     * @return
     */
    public SymbolsResponse symbols(String symbol,boolean needSignature) {
        HashMap map = new HashMap();
        map.put("symbol", symbol);
        SymbolsResponse resp = get("/v1/common/symbols", map, new TypeReference<SymbolsResponse<Symbol>>() {
        },needSignature);
        return resp;
    }

    /**
     * GET /v1/common/symbols 查询系统支持的所有交易对及精度
     *
     * @return
     */
    public SymbolsResponse symbols(boolean needSignature) {
        //HashMap map = new HashMap();
        SymbolsResponse resp = get("/v1/common/symbols", null, new TypeReference<SymbolsResponse<List<Symbol>>>() {
        },needSignature);
        return resp;
    }


    /**
     * GET /v1/common/currencys 查询系统支持的所有币种
     *
     * @param symbol
     * @return
     */
    public CurrencysResponse currencys(String symbol,boolean needSignature) {
        HashMap map = new HashMap();
        map.put("symbol", symbol);
        CurrencysResponse resp = get("/v1/common/currencys", map, new TypeReference<CurrencysResponse>() {
        },needSignature);
        return resp;
    }

    public CurrencysResponse currencys(boolean needSignature) {
        CurrencysResponse resp = get("/v1/common/currencys", null, new TypeReference<CurrencysResponse>() {
        },needSignature);
        return resp;
    }
    /**
     * GET /v1/common/timestamp 查询系统当前时间
     *
     * @return
     */
    public TimestampResponse timestamp(boolean needSignature) {
        TimestampResponse resp = get("/v1/common/timestamp", null, new TypeReference<TimestampResponse>() {
        },needSignature);
        return resp;
    }

    /**
     * GET /v1/account/accounts 查询当前用户的所有账户(即account-id)
     *
     * @return
     */
    public AccountsResponse accounts(boolean needSignature) {
        AccountsResponse resp = get("/v1/account/accounts", null, new TypeReference<AccountsResponse<List<Accounts>>>() {
        }, needSignature);
        return resp;
    }

    /**
     * GET /v1/account/accounts/{account-id}/balance 查询指定账户的余额
     *
     * @param accountId
     * @return
     */
    public BalanceResponse balance(String accountId,boolean needSignature) {
        BalanceResponse resp = get("/v1/account/accounts/" + accountId + "/balance", null, new TypeReference<BalanceResponse<Balance>>() {
        },needSignature);
        return resp;
    }

    /**
     * POST /v1/order/orders/{order-id}/submitcancel 申请撤销一个订单请求
     *
     * @param orderId
     * @return
     */
    public SubmitcancelResponse submitcancel(String orderId) {
        SubmitcancelResponse resp = post("/v1/order/orders/" + orderId + "/submitcancel", null, new TypeReference<SubmitcancelResponse>() {
        });
        return resp;
    }

    /**
     * POST /v1/order/orders/batchcancel 批量撤销订单
     *
     * @param orderList
     * @return
     */
    public BatchcancelResponse submitcancels(List orderList) {
          Map<String, List> parameterMap = new HashMap();
          parameterMap.put("order-ids", orderList);
          BatchcancelResponse resp = post("/v1/order/orders/batchcancel", parameterMap, new TypeReference<BatchcancelResponse<Batchcancel<List, List<BatchcancelBean>>>>() {
          });
          return resp;
    }

    /**
     * GET /v1/order/orders/{order-id} 查询某个订单详情
     *
     * @param orderId
     * @return
     */
    public OrdersDetailResponse ordersDetail(String orderId) {
        OrdersDetailResponse resp = get("/v1/order/orders/" + orderId, null, new TypeReference<OrdersDetailResponse>() {
        });
        return resp;
    }


    /**
     * GET /v1/order/orders/{order-id}/matchresults 查询某个订单的成交明细
     *
     * @param orderId
     * @return
     */
    public MatchresultsOrdersDetailResponse matchresults(String orderId) {
        MatchresultsOrdersDetailResponse resp = get("/v1/order/orders/" + orderId + "/matchresults", null, new TypeReference<MatchresultsOrdersDetailResponse>() {
        });
        return resp;
    }

    public IntrustDetailResponse intrustOrdersDetail(IntrustOrdersDetailRequest req) {
        HashMap map = new HashMap();
        map.put("symbol", req.symbol);
        map.put("states", req.states);
        if (req.startDate!=null) {
            map.put("startDate",req.startDate);
 		}
         if (req.startDate!=null) {
             map.put("start-date",req.startDate);
  		}
         if (req.endDate!=null) {
             map.put("end-date",req.endDate);
  		}
         if (req.types!=null) {
             map.put("types",req.types);
  		}
         if (req.from!=null) {
             map.put("from",req.from);
  		}
         if (req.direct!=null) {
             map.put("direct",req.direct);
  		}
         if (req.size!=null) {
             map.put("size",req.size);
  		}
        IntrustDetailResponse resp = get("/v1/order/orders/", map, new TypeReference<IntrustDetailResponse<List<IntrustDetail>>>() {
        });
        return resp;
    }

//  public IntrustDetailResponse getALlOrdersDetail(String orderId) {
//    IntrustDetailResponse resp = get("/v1/order/orders/"+orderId, null,new TypeReference<IntrustDetailResponse>() {});
//    return resp;
//  }


    // send a GET request.
    <T> T get(String uri, Map<String, String> params, TypeReference<T> ref, boolean needSignature) {
        if (needSignature){
            if (params == null) {
                params = new HashMap<>();
            }
        }
        return call("GET", uri, null, params, ref);
    }

    <T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
        if (params == null) {
            params = new HashMap<>();
        }
        return call("GET", uri, null, params, ref);
    }


    // send a POST request.
    <T> T post(String uri, Object object, TypeReference<T> ref) {
        return call("POST", uri, object, new HashMap<String, String>(), ref);
    }

    // call api by endpoint.
    <T> T call(String method, String uri, Object object, Map<String, String> params,
               TypeReference<T> ref) {
        SortedMap<String, String> signatureMap = new TreeMap<>();
      /*  System.setProperty("https.protocols", "TLSv1.0");
        String proxyHost = "127.0.0.1";
        String proxyPort = "11518";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

        // 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);*/
        ApiSignature sign = new ApiSignature();
        if (params!=null){
            signatureMap = sign.createSignature(this.accessKeyId, this.accessKeySecret, method, API_HOST, uri, params);
        }
        try {
            Request.Builder builder = null;
            if ("POST".equals(method)) {
                RequestBody body = RequestBody.create(JSON, JsonUtil.writeValue(object));
                if (!CollectionUtils.isEmpty(params)){
                    builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(signatureMap)).post(body);
                }else{
                    builder = new Request.Builder().url(API_URL + uri ).post(body);
                }

            } else {
                //.addHeader("Connection", "close")
                //.header("https.protocols", "TLSv1.0")
                if (!CollectionUtils.isEmpty(params)){
                    builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(signatureMap)).get();
                }else{
                    builder = new Request.Builder().url(API_URL + uri ).get();
                }

            }
            if (this.assetPassword != null) {
                builder.addHeader("AuthData", authData());
            }
                Request request = builder.build();

            Response response = client.newCall(request).execute();
            logger.info("response " + response.code());

            String s = response.body().string();
            return JsonUtil.readValue(s, ref);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw new ApiException(e);
        }
    }

    String authData() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(this.assetPassword.getBytes(StandardCharsets.UTF_8));
        md.update("hello, moto".getBytes(StandardCharsets.UTF_8));
        Map<String, String> map = new HashMap<>();
        map.put("assetPwd", DatatypeConverter.printHexBinary(md.digest()).toLowerCase());
        try {
            return ApiSignature.urlEncode(JsonUtil.writeValue(map));
        } catch (IOException e) {
            throw new RuntimeException("Get json failed: " + e.getMessage());
        }
    }

    // Encode as "a=1&b=%20&c=&d=AAA"
    String toQueryString(Map<String, String> params) {
        return String.join("&", params.entrySet().stream().map((entry) -> {
            return entry.getKey() + "=" + ApiSignature.urlEncode(entry.getValue());
        }).collect(Collectors.toList()));
    }

    // create OkHttpClient:
    static OkHttpClient createOkHttpClient() {
       /* SSLContext sslContext = null;
        SSLSocketFactory sslSocketFactory = null;
        try {
            // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
            final X509TrustManager trustAllCert =
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        //
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        //
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    };
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[]{trustAllCert}, null);
            sslSocketFactory = sslContext.getSocketFactory();*/
            client = new Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 41814)))
                    //.sslSocketFactory(sslSocketFactory, trustAllCert)
                    .socketFactory(SSLSocketFactory.getDefault())
                    .build();
      /*  } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }*/
        return client;
    }




        public static OkHttpClient getClient(){
            //OkHttpClient sclient =null;
            if (client == null) {
                synchronized (ApiClient.class) {
                    if (client == null) {
                        Builder builder = new Builder();
                        SSLSocketFactory sslSocketFactory = null;

                        try {
                            // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                            final X509TrustManager trustAllCert =
                                    new X509TrustManager() {
                                        @Override
                                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                        }

                                        @Override
                                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                        }

                                        @Override
                                        public X509Certificate[] getAcceptedIssuers() {
                                            return new X509Certificate[]{};
                                        }
                                    };
                            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                            sslContext.init(null, new TrustManager[]{trustAllCert}, null);
                            sslSocketFactory = sslContext.getSocketFactory();
                            client = new Builder()
                                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 41814)))
                                    .sslSocketFactory(sslSocketFactory, trustAllCert)
                                    .build();
                            /*final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                            builder.sslSocketFactory(sslSocketFactory, trustAllCert);*/
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        return client;
    }


    static String getHost() {
        String host = null;
        try {
            host = new  URL(API_URL).getHost();
        } catch (MalformedURLException e) {
            System.err.println("parse API_URL error,system exit!,please check API_URL:" + API_URL ); 
            System.exit(0);
        }
        return host;
    }

}






