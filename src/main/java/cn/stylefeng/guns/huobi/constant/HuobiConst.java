package cn.stylefeng.guns.huobi.constant;

public class HuobiConst {

    public enum peroid{
        ONE_MIN ("1min","HH:mm"),
        FIVE_MIN("5min","HH:mm"),
        FIFTH_MIN("15min","HH:mm"),
        THIRTY_MIN ("30min","HH:mm"),
        ONE_HOUR("1hour","HH:mm"),
        FOUR_HOUR("4hour","HH:mm"),
        ONE_DAY("1day","yyyy-MM-dd"),
        FIVE_DAY("5day","yyyy-MM-dd"),
        ONE_WEEK("1week","yyyy-MM-dd"),
        ONE_MON("1mon","yyyy-MM-dd");

        private String peroid;
        private String dateFormat;

        peroid(String peroid, String dateFormat) {
            this.peroid = peroid;
            this.dateFormat = dateFormat;
        }

        public String getPeroid() {
            return peroid;
        }

        public String getDateFormat() {
            return dateFormat;
        }
    }


}
