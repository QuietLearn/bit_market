package cn.stylefeng.guns.modular.huobi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * Pro站支持的所有交易对及精度
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@TableName("symbols")
public class Symbols extends Model<Symbols> {

    private static final long serialVersionUID = 1L;

    /**
     * 基础币种
     */
    private String baseCurrency;
    /**
     * 计价币种
     */
    private String quoteCurrency;
    /**
     * 价格精度位数（0为个位，ethbtc, etcbtc, bchbtc, ltcbtc 除外，这四个交易对的精度固定为4位 ）
     */
    private String pricePrecision;
    /**
     * 数量精度位数（0为个位，指 base-currency 数量）
     */
    private String amountPrecision;
    /**
     * 交易区(main主区，innovation创新区，bifurcation分叉区)
     */
    private String symbolPartition;

    public String symbol;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getPricePrecision() {
        return pricePrecision;
    }

    public void setPricePrecision(String pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    public String getAmountPrecision() {
        return amountPrecision;
    }

    public void setAmountPrecision(String amountPrecision) {
        this.amountPrecision = amountPrecision;
    }

    public String getSymbolPartition() {
        return symbolPartition;
    }

    public void setSymbolPartition(String symbolPartition) {
        this.symbolPartition = symbolPartition;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Symbols{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", quoteCurrency='" + quoteCurrency + '\'' +
                ", pricePrecision='" + pricePrecision + '\'' +
                ", amountPrecision='" + amountPrecision + '\'' +
                ", symbolPartition='" + symbolPartition + '\'' +
                '}';
    }
}
