package cn.stylefeng.guns.modular.huobi.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-21
 */
@TableName("kline_divide")
public class KlineDivide extends Model<KlineDivide> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("kd_open")
    private Double open;
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
    @TableField("kd_ts")
    private Long ts;
    @TableField("gmt_response")
    private Date gmtResponse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Date getGmtResponse() {
        return gmtResponse;
    }

    public void setGmtResponse(Date gmtResponse) {
        this.gmtResponse = gmtResponse;
    }

    @Override
    public String toString() {
        return "KlineDivide{" +
                "id=" + id +
                ", open=" + open +
                ", close=" + close +
                ", low=" + low +
                ", high=" + high +
                ", vol=" + vol +
                ", amount=" + amount +
                ", count=" + count +
                ", symbol='" + symbol + '\'' +
                ", ts=" + ts +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
