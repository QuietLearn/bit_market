package cn.stylefeng.guns.modular.huobi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyj
 * @since 2018-12-14
 */
@TableName("depth")
public class Depth extends Model<Depth> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private Long id;
    /**
     * 买盘,[price(成交价), amount(成交量)], 按price降序
     */
    private List<List<BigDecimal>> bids;
    /**
     * 卖盘,[price(成交价), amount(成交量)], 按price升序
     */
    private List<List<BigDecimal>> asks;

    private String bidString;

    private String askString;
    /**
     * 消息生成时间，单位：毫秒
     */
    private Long ts;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<List<BigDecimal>> getBids() {
        return bids;
    }

    public void setBids(List<List<BigDecimal>> bids) {
        this.bids = bids;
        setBidString(null);
    }

    public List<List<BigDecimal>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<BigDecimal>> asks) {
        this.asks = asks;
        setAskString(null);
    }

    public String getBidString() {
        return bidString;
    }

    public String getAskString() {
        return askString;
    }

    public void setBidString(String bidString) {
        if (CollectionUtils.isNotEmpty(bids)) {
            JSONArray jsonArray = JSONArray.fromObject(this.bids);
            this.bidString = jsonArray.toString();
        } else {
            this.bidString = bidString;
            ObjectMapper objmapper = new ObjectMapper();
            try {
                @SuppressWarnings("unchecked")
                List<List<BigDecimal>> list = objmapper.readValue(this.bidString,List.class);//将json字符串转化成list
                setBids(list);//调用setStar方法
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setAskString(String askString) {
        if (CollectionUtils.isNotEmpty(asks)){
            JSONArray jsonArray = JSONArray.fromObject(this.asks);
            this.askString = jsonArray.toString();
        } else {
            this.askString = askString;
            ObjectMapper objmapper = new ObjectMapper();
            try {
                @SuppressWarnings("unchecked")
                List<List<BigDecimal>> list = objmapper.readValue(this.askString,List.class);//将json字符串转化成list
                setAsks(list);//调用setStar方法
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depth depth = (Depth) o;
        return id.equals(depth.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Depth{" +
        ", id=" + id +
        ", bids=" + bids +
        ", asks=" + asks +
        ", ts=" + ts +
        "}";
    }
}
