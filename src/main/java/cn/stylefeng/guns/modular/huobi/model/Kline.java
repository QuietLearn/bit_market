package cn.stylefeng.guns.modular.huobi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@TableName("kline")
public class Kline extends Model<Kline> {

    private static final long serialVersionUID = 1L;

    /**
     * K线id（时间戳）
     */
    private Integer id;
    /**
     * 成交量
     */
    private Double amount;
    /**
     * 成交笔数
     */
    private Integer count;
    /**
     * 开盘价
     */
    private Double open;
    /**
     * 收盘价,当K线为最晚的一根时，是最新成交价
     */
    private Double close;
    /**
     * 最低价
     */
    private Double low;
    /**
     * 最高价
     */
    private Double high;
    /**
     * 成交额, 即 sum(每一笔成交价 * 该笔的成交量)
     */
    private Double vol;

    private String symbol;
    private String peroid;

    private Date gmtCreated;
    private Date gmtUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtUpdated() {
        return gmtUpdated;
    }

    public void setGmtUpdated(Date gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    public String getPeroid() {
        return peroid;
    }

    public void setPeroid(String peroid) {
        this.peroid = peroid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kline)) return false;
        Kline kline = (Kline) o;
        return getId().equals(kline.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Kline{" +
        ", id=" + id +
        ", amount=" + amount +
        ", count=" + count +
        ", open=" + open +
        ", close=" + close +
        ", low=" + low +
        ", high=" + high +
        ", vol=" + vol +
        "}";
    }
}
