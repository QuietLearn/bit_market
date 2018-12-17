package cn.stylefeng.guns.modular.huobi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@TableName("tickers")
public class Tickers extends Model<Tickers> {

    private static final long serialVersionUID = 1L;

    /**
     * 日K线 收盘价
     */
    private Double close;
    /**
     * 日K线 开盘价
     */
    private Double open;
    /**
     * 日K线 最高价
     */
    private Double high;
    /**
     * 日K线 最低价
     */
    private Double low;
    /**
     * 24小时成交量
     */
    private Double amount;
    /**
     * 24小时成交笔数
     */
    private Integer count;
    /**
     * 24小时成交额
     */
    private Double vol;
    /**
     * 交易对
     */
    private String symbol;
    /**
     * 响应生成时间点，单位：毫秒
     */
    private Long ts;


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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Tickers{" +
        ", close=" + close +
        ", open=" + open +
        ", high=" + high +
        ", low=" + low +
        ", amount=" + amount +
        ", count=" + count +
        ", vol=" + vol +
        ", symbol=" + symbol +
        ", ts=" + ts +
        "}";
    }
}
