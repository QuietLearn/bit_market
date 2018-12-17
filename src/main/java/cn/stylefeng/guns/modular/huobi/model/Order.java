package cn.stylefeng.guns.modular.huobi.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-17
 */
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
    public String toString() {
        return "Order{" +
        ", price=" + price +
        ", amount=" + amount +
        ", type=" + type +
        "}";
    }
}
