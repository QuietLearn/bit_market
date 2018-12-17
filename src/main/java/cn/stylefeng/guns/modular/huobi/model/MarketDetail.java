package cn.stylefeng.guns.modular.huobi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 滚动24小时交易聚合行情(单个symbol)
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@TableName("market_detail")
public class MarketDetail extends Model<MarketDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private Long id;
    /**
     * 24小时成交量
     */
    private Double amount;
    /**
     * 近24小时累积成交数
     */
    private Integer count;
    /**
     * 前推24小时成交价
     */
    private Double open;
    /**
     * 当前成交价
     */
    private Double close;
    /**
     * 近24小时最低价
     */
    private Double low;
    /**
     * 近24小时最高价
     */
    private Double high;
    /**
     * 近24小时累积成交额, 即 sum(每一笔成交价 * 该笔的成交量)
     */
    private Double vol;
    /**
     * 24小时统计时间,
     */
    private Long ts;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "MarketDetail{" +
        ", id=" + id +
        ", amount=" + amount +
        ", count=" + count +
        ", open=" + open +
        ", close=" + close +
        ", low=" + low +
        ", high=" + high +
        ", vol=" + vol +
        ", ts=" + ts +
        "}";
    }
}
