package cn.stylefeng.guns.modular.huobi.model;

import cn.stylefeng.guns.huobi.util.JsonUtil;
import com.alibaba.fastjson.support.hsf.HSFJSONUtils;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@TableName("merged")
public class Merged extends Model<Merged> {

    private static final long serialVersionUID = 1L;

    /**
     * K线id（时间戳）
     */
    private Long id;
    /**
     * 收盘价,当K线为最晚的一根时，是最新成交价
     */
    private Double close;
    /**
     * 开盘价
     */
    private Double open;
    /**
     * 最高价
     */
    private Double high;
    /**
     * 最低价
     */
    private Double low;
    /**
     * 成交量
     */
    private Double amount;
    /**
     * 成交笔数
     */
    private Integer count;
    /**
     * 成交额, 即 sum(每一笔成交价 * 该笔的成交量)
     */
    private Double vol;
    /**
     * [买1价,买1量]
     */
    private List<BigDecimal> bid;
    /**
     * [卖1价,卖1量]
     */
    private List<BigDecimal> ask;


    private String bidString;

    private String askString;
    /**
     * 响应生成时间点，单位：毫秒
     */
    private Long ts;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getVol() {
        return vol;
    }

    public void setVol(Double vol) {
        this.vol = vol;
    }

    public List<BigDecimal> getBid() {
        return bid;
    }

    public void setBid(List<BigDecimal> bid) {
        this.bid = bid;
        setBidString(null);
    }

    public List<BigDecimal> getAsk() {
        return ask;
    }

    public void setAsk(List<BigDecimal> ask) {
        this.ask = ask;
        setAskString(null);
    }

    public String getBidString() {
        return bidString;
    }

    public void setBidString(String bidString) {
        if (CollectionUtils.isNotEmpty(bid)) {
            JSONArray jsonArray = JSONArray.fromObject(this.bid);
            this.bidString = jsonArray.toString();
        } else {
            this.bidString = bidString;
            ObjectMapper objmapper = new ObjectMapper();
            try {
                @SuppressWarnings("unchecked")
                List<BigDecimal> list = objmapper.readValue(bidString,List.class);//将json字符串转化成list
                setBid(list);//调用setStar方法

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getAskString() {
        return askString;
    }

    public void setAskString(String askString) {
        if (CollectionUtils.isNotEmpty(ask)) {
            JSONArray jsonArray = JSONArray.fromObject(this.ask);
            this.askString = jsonArray.toString();
        } else {
            this.askString = askString;
            ObjectMapper objmapper = new ObjectMapper();
            try {
                @SuppressWarnings("unchecked")
                List<BigDecimal> list = objmapper.readValue(askString, List.class);//将json字符串转化成list
                setAsk(list);//调用setStar方法
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Merged{" +
        ", id=" + id +
        ", close=" + close +
        ", open=" + open +
        ", high=" + high +
        ", low=" + low +
        ", amount=" + amount +
        ", count=" + count +
        ", vol=" + vol +
        ", bid=" + bid +
        ", ask=" + ask +
        ", ts=" + ts +
        "}";
    }
}
