package cn.stylefeng.guns.modular.huobi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@TableName("trade_detail")
public class TradeDetail extends Model<TradeDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private Long id;
    /**
     * 成交id
     */
    private Long bargainId;
    /**
     * 成交价钱
     */
    private Double price;
    /**
     * 成交量
     */
    private Double amount;
    /**
     * 主动成交方向
     */
    private String direction;
    /**
     * 最新成交时间
     */
    private Long ts;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBargainId() {
        return bargainId;
    }

    public void setBargainId(Long bargainId) {
        this.bargainId = bargainId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeDetail that = (TradeDetail) o;
        return bargainId.equals(that.bargainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bargainId);
    }

    @Override
    public String toString() {
        return "TradeDetail{" +
        ", id=" + id +
        ", bargainId=" + bargainId +
        ", price=" + price +
        ", amount=" + amount +
        ", direction=" + direction +
        ", ts=" + ts +
        "}";
    }
}
