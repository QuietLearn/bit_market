package cn.stylefeng.guns.modular.huobi.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2018-12-18
 */
@TableName("bit_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 成交价
     */
    private BigDecimal price;
    /**
     * 成交量
     */
    private BigDecimal amount;
    /**
     * 0 买盘bids   1 卖盘asks
     */
    private Integer type;

    private Long ts;

    @TableField("gmt_created")
    private Date gmtCreated;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return price.equals(order.price) &&
                amount.equals(order.amount) &&
                type.equals(order.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, amount, type);
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    public String toString() {
        return "Order{" +
        ", price=" + price +
        ", amount=" + amount +
        ", type=" + type +
        "}";
    }
}
