package cn.stylefeng.guns.huobi.constant;

public class HuobiConst {

    public interface KlineCombineChartAxisTickUnit{
        int x1MiddleAxisTickUnit = 40;
        int x1BigAxisTickUnit = 150;
        int x1HugeTickUnit = 400;
    }


    public enum peroid{
        ONE_MIN ("1min","HH:mm"),
        FIVE_MIN("5min","HH:mm"),
        FIFTH_MIN("15min","HH:mm"),
        THIRTY_MIN ("30min","HH:mm"),
        SIXTY_MIN("60min","HH:mm"),
        ONE_DAY("1day","yyyy-MM-dd"),
        ONE_WEEK("1week","yyyy-MM-dd"),
        ONE_MON("1mon","yyyy-MM-dd"),
        ONE_YEAR("1year","yyyy-MM-dd");

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
