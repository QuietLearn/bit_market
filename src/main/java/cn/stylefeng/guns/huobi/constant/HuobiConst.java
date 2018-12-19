package cn.stylefeng.guns.huobi.constant;

public class HuobiConst {

    public enum peroid{
        ONE_MIN ("1min"),
        FIVE_MIN("5min"),
        FIFTH_MIN("15min"),
        THIRTY_MIN ("30min"),
        ONE_HOUR("1hour"),
        FOUR_HOUR("4hour"),
        ONE_DAY("1day"),
        FIVE_DAY("5day"),
        ONE_WEEK("1week"),
        ONE_MON("1mon");

        private String peroid;
        peroid(String peroid){
            this.peroid = peroid;
        }

        public String getPeroid() {
            return peroid;
        }


    }
}
