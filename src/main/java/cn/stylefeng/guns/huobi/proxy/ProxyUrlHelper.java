package cn.stylefeng.guns.huobi.proxy;

import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**所有需要添加代理服务器的url请调用这个类进行代理服务器添加
 * @author: da
 * @version: 1.0
 */

/**
 * So the way a ProxySelector works is that for all Connections made,
 * it delegates to a proxySelector(There is a default we're going to
 * override with this class) to know if it needs to use a proxy
 * for the connection.
 * <p>This class was specifically created with the intent to proxy connections
 * going to the allegiance soap service.</p>
 *
 */
public class ProxyUrlHelper extends ProxySelector {

    private final ProxySelector def ;

    private static Map<String,List<Proxy>> proxyMap = new ConcurrentHashMap<>();

    private Logger log = LoggerFactory.getLogger(getClass());;


    public void addProxy(String destUri,String proxyHost, String proxyPort){

        Assert.hasLength(destUri,"destUri can't be null or empty.");

        URI uri;
        try{
            uri = new URI(destUri);
        }catch(URISyntaxException e){
            throw new IllegalArgumentException(e.getMessage());
        }

        if(proxyHost!=null&&(!"".equals(proxyHost.trim()))){
            String host = uri.getHost();
            if(!proxyMap.containsKey(host)){
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, (null == proxyPort) ? 80 : Integer.valueOf(proxyPort)));
                List<Proxy> proxyList = new ArrayList<>();
                proxyList.add(proxy);
                proxyMap.put(host,proxyList);
            }else{
                List<Proxy> proxyList = proxyMap.get(host);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, (null == proxyPort) ? 80 : Integer.valueOf(proxyPort)));
                proxyList.add(proxy);
            }
        }

        ProxySelector.setDefault(this);
    }

    public ProxyUrlHelper() {
        def = ProxySelector.getDefault();
    }


    @Override
    public List<Proxy> select(URI uri) {//这里是每一个连接都会进行是否需要添加代理服务器的判断

        Assert.notNull(uri, "URI can't be null.");

        if(proxyMap.size()>0){
            String host = uri.getHost();
            if(proxyMap.containsKey(host)){//只有需要添加代理服务器的请求才用代理服务器
                log.info("We're trying to reach "+uri+" so we're going to use the extProxy.");
                return proxyMap.get(host);
            }
        }

        return def.select(uri);

    }

    /**
     * Method called by the handlers when it failed to connect
     * to one of the proxies returned by select().
     */
    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        log.error("Failed to connect to a proxy when connecting to " + uri.getHost());
        def.connectFailed(uri, sa, ioe);
    }


    public static void main(String[] args) {
        ProxyUrlHelper proxyUrlHelper= new ProxyUrlHelper();
        proxyUrlHelper.addProxy("https://api.huobi.pro","127.0.0.1","11518");
        try {
            List<Proxy> select = proxyUrlHelper.select(new URI("https://api.huobi.pro"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
