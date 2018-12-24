package cn.stylefeng.guns.modular.huobi.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-24
 */
@TableName("kline_divide")
public class KlineDivide extends Model<KlineDivide> {

    private static final long serialVersionUID = 1L;

    @TableField("kd_ts")
    private Long kdTs;
    @TableField("gmt_response")
    private Date gmtResponse;
    @TableField("kd_open")
    private Double open;
    /**
     * 最新成交价(可以根据其算出高开低收)
     */
    @TableField("kd_close")
    private Double close;
    @TableField("kd_low")
    private Double low;
    @TableField("kd_high")
    private Double high;
    @TableField("kd_vol")
    private Double vol;
    @TableField("kd_amount")
    private Double amount;
    @TableField("kd_count")
    private Integer count;
    @TableField("kd_symbol")
    private String symbol;
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
   /* private BigDecimal ask;
    @TableField("ask_quantity")
    private BigDecimal askQuantity;
    private BigDecimal bid;
    @TableField("bid_quantity")
    private BigDecimal bidQuantity;*/


    public Long getKdTs() {
        return kdTs;
    }

    public void setKdTs(Long kdTs) {
        this.kdTs = kdTs;
    }

    public Date getGmtResponse() {
        return gmtResponse;
    }

    public void setGmtResponse(Date gmtResponse) {
        this.gmtResponse = gmtResponse;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getVol() {
        return vol;
    }

    public void setVol(Double vol) {
        this.vol = vol;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<BigDecimal> getBid() {
        return bid;
    }

    public void setBid(List<BigDecimal> bid) {
        this.bid = bid;
    }

    public List<BigDecimal> getAsk() {
        return ask;
    }

    public void setAsk(List<BigDecimal> ask) {
        this.ask = ask;
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

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "KlineDivide{" +
                "kdTs=" + kdTs +
                ", gmtResponse=" + gmtResponse +
                ", open=" + open +
                ", close=" + close +
                ", low=" + low +
                ", high=" + high +
                ", vol=" + vol +
                ", amount=" + amount +
                ", count=" + count +
                ", symbol='" + symbol + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", bidString='" + bidString + '\'' +
                ", askString='" + askString + '\'' +
                '}';
    }
}
