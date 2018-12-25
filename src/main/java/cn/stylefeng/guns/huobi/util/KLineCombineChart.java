package cn.stylefeng.guns.huobi.util;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;//时间格式
import java.util.*;
import java.util.List;

import cn.stylefeng.guns.huobi.api.ApiClient;
import cn.stylefeng.guns.huobi.api.Main;
import cn.stylefeng.guns.huobi.constant.HuobiConst;
import cn.stylefeng.guns.huobi.response.KlineResponse;
import cn.stylefeng.guns.modular.huobi.dao.KlineDivideMapper;
import cn.stylefeng.guns.modular.huobi.dao.KlineMapper;
import cn.stylefeng.guns.modular.huobi.model.Kline;
import cn.stylefeng.guns.modular.huobi.model.KlineDivide;
import cn.stylefeng.guns.modular.huobi.service.IKlineService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.commons.collections.CollectionUtils;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.data.time.*;
import org.jfree.data.time.Day;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.*;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.Border;

@Component
@Order(1)
public class KLineCombineChart implements CommandLineRunner{
    private static final int EXTENT = 30;

    private static String global_period ="5min";

    private static String global_symbol ="btcusdt";


    Calendar cal=Calendar.getInstance();
    int day = cal.get(Calendar.DATE);
    int month = cal.get(Calendar.MONTH)+1;
    int year = cal.get(Calendar.YEAR);

    OHLCSeries series ;
    TimeSeries series2 ;
    List<Kline> klineList ;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;

    @Autowired
    private IKlineService klineService;
    @Autowired
    private KlineMapper klineMapper;
    @Autowired
    private KlineDivideMapper klineDivideMapper;
    @Autowired
    private Main main;

    @Override
    public void run(String... args) throws Exception {
        generateKline();
    }

    public void autoRefresh(){
        Kline latestKline = klineMapper.selectLatest(global_period, global_symbol);
        KlineDivide klineDivide = klineDivideMapper.selectLatest(global_symbol);
        jLabel1.setText(""+klineDivide.getClose());
        jLabel2.setText(""+klineDivide.getBid().get(0));
        jLabel3.setText(""+klineDivide.getBid().get(1));
        jLabel4.setText(""+klineDivide.getAsk().get(0));
        jLabel5.setText(""+klineDivide.getAsk().get(1));
        //klineList.sort();
        if (klineList.get(klineList.size()-1).getId().equals(latestKline.getId()))
            return;

        klineList.add(latestKline);
        insertKlineToSeries(latestKline);

    }

    public void insertKlineToSeries(Kline kline){
        Date date = new Date(kline.getId()*1000l);

        Minute minute = new Minute(date);
        series.add(minute, kline.getOpen(), kline.getHigh(), kline.getLow(), kline.getClose());
        series2.add(minute, kline.getAmount());
    }


    public void generateKline() {
        this.klineList = klineMapper.selectAllKline(global_period,global_symbol);
        if (CollectionUtils.isEmpty(klineList)){
            KlineResponse klineListResponse = klineService.getAndInsertKlineData("btcusdt","5min",new ApiClient(main.API_KEY, main.API_SECRET),200,true);
            this.klineList = (List<Kline>)klineListResponse.getData();
            Collections.sort(klineList);
        }



        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值
        double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值
        double high2Value = Double.MIN_VALUE;//设置成交量的最大值
        double min2Value = Double.MAX_VALUE;//设置成交量的最低值

        /*Wrapper wrapper = new EntityWrapper<Kline>();
        wrapper.orderBy("id",false);
        wrapper.eq("peroid","5min");*/



        //高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
        series = new OHLCSeries("");
        //对应时间成交量数据
        series2 = new TimeSeries("");

        //保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
        final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
        //保留成交量数据的集合
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

        int eDay = day;
        int eMonth = month;
        int eYear = year;
        int p = EXTENT;
        Day today = new Day(day, month, year);
        Minute minute;
        for (Kline kline : this.klineList) {
            insertKlineToSeries(kline);
           /* if(p>=0){
                p--;
            }*/
        }
        seriesCollection.addSeries(series);
        timeSeriesCollection.addSeries(series2);

        //获取K线数据的最高值和最低值
        int seriesCount = seriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
        for (int i = 0; i < seriesCount; i++) {
            int itemCount = seriesCollection.getItemCount(i);//每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (highValue < seriesCollection.getHighValue(i, j)) {//取第i个序列中的第j个数据项的最大值
                    highValue = seriesCollection.getHighValue(i, j);
                }
                if (minValue > seriesCollection.getLowValue(i, j)) {//取第i个序列中的第j个数据项的最小值
                    minValue = seriesCollection.getLowValue(i, j);
                }
            }
        }

        //获取timeSeriesCollection最高值和最低值
        int seriesCount2 = timeSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
        for (int i = 0; i < seriesCount2; i++) {
            int itemCount = timeSeriesCollection.getItemCount(i);//每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (high2Value < timeSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                    high2Value = timeSeriesCollection.getYValue(i, j);
                }
                if (min2Value > timeSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                    min2Value = timeSeriesCollection.getYValue(i, j);
                }
            }

        }
        //设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
        //蜡烛图画图器
        final CandlestickRenderer candlestickRender = new CandlestickRenderer();
        candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
        candlestickRender.setUpPaint(Color.GREEN);//设置股票上涨的K线图颜色
        candlestickRender.setDownPaint(Color.RED);//设置股票下跌的K线图颜色
        DateAxis x1Axis = new DateAxis();//设置x轴，也就是时间轴
        x1Axis.setAutoRange(false);//设置不采用自动设置时间范围
       /* try {
            //dateFormat.parse()
            //dateFormat.parse("2018-11-18")
            Calendar calendar = Calendar.getInstance();//日历对象
            calendar.setTime(new Date());//设置当前日期
            calendar.add(Calendar.MONTH, -1);//月份减一
            x1Axis.setRange(calendar.getTime(), new Date());//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
        x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
        x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
        x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位

        if(series.getItemCount()> HuobiConst.KlineCombineChartAxisTickUnit.x1HugeTickUnit){
            x1Axis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1));
            x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
        } else if(series.getItemCount()> HuobiConst.KlineCombineChartAxisTickUnit.x1BigAxisTickUnit){
            x1Axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 7));
            x1Axis.setDateFormatOverride(new SimpleDateFormat("MM:dd"));
        } else if(series.getItemCount()> HuobiConst.KlineCombineChartAxisTickUnit.x1MiddleAxisTickUnit){
            x1Axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));
            x1Axis.setDateFormatOverride(new SimpleDateFormat("MM:dd"));//设置显示时间的格式
        } else {
            x1Axis.setTickUnit(new DateTickUnit(DateTickUnitType.MINUTE, 15));//设置时间刻度的间隔，一般以周为单位
            x1Axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));//设置显示时间的格式
        }




        NumberAxis y1Axis = new NumberAxis();//设定y轴，就是数字轴
        y1Axis.setAutoRange(false);//不不使用自动设定范围
        y1Axis.setRange(minValue * 0.99, highValue * 1.01);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
        y1Axis.setTickUnit(new NumberTickUnit((highValue * 1.01 - minValue * 0.99) / 10));//设置刻度显示的密度
        XYPlot plot1 = new XYPlot(seriesCollection, x1Axis, y1Axis, candlestickRender);//设置画图区域对象


        XYBarRenderer xyBarRender = new XYBarRenderer() {
            private static final long serialVersionUID = 1L;//为了避免出现警告消息，特设定此值

            public Paint getItemPaint(int i, int j) {//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
                if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
                    return candlestickRender.getUpPaint();
                } else {
                    return candlestickRender.getDownPaint();
                }
            }
        };
        xyBarRender.setMargin(0.1);//设置柱形图之间的间隔
        NumberAxis y2Axis = new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
        y2Axis.setAutoRange(false);
        y2Axis.setRange(min2Value * 0.99, high2Value * 1.01);
        y2Axis.setTickUnit(new NumberTickUnit((high2Value * 1.01 - min2Value * 0.99) / 4));
        XYPlot plot2 = new XYPlot(timeSeriesCollection, null, y2Axis, xyBarRender);//建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
        CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴

        combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
        combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
        combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间

        JFreeChart chart = new JFreeChart("比特币", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.add();


        JPanel jpanel = new JPanel();
        jpanel.setBorder(BorderFactory.createEmptyBorder(1,4,2,4));//边距为4
        List<JButton> jButtonList = Lists.newArrayList();

        ChartFrame frame = new ChartFrame("比特币行情", chart);

        JButton jButton = new JButton("同步");
        jButton.setActionCommand("sync");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("sync")){
                    klineService.getAndInsertKlineData(global_symbol,global_period,new ApiClient(main.API_KEY,main.API_SECRET),2000,false);
                }
        }
        });
        jpanel.add(jButton);

        JPanel buySellJpanel = this.createBuySellJpanel();

        for (HuobiConst.peroid peroid:HuobiConst.peroid.values()) {
            JButton jbutton = new JButton(peroid.getPeroid());
            jbutton.setActionCommand(peroid.getPeroid());
            jbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getActionCommand().equals(peroid.getPeroid())){
                        //klineService.setPeriodFromButton(peroid.getPeroid());
                        global_period = peroid.getPeroid();
                        Wrapper wrapper2 = new EntityWrapper<Kline>();
                        wrapper2.eq("peroid",peroid.getPeroid());
                        wrapper2.eq("k_symbol",global_symbol);
                        KLineCombineChart.this.klineList = klineService.selectList(wrapper2);

                        series.clear();
                        series2.clear();
                        Minute minute;
                        for (Kline kline : KLineCombineChart.this.klineList) {
                            Date date = new Date(kline.getId()*1000l);
                            minute = new Minute(date);
                            series.add(minute, kline.getOpen(), kline.getHigh(), kline.getLow(), kline.getClose());
                            series2.add(minute, kline.getAmount());
                        }
                        //frame.repaint();
                        //thread1.destroy();
                    }

                }
            });
            jpanel.add(jbutton);
        }


        frame.add(jpanel,"north");
        frame.add(buySellJpanel,"west");
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createBuySellJpanel(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2,5,10,5));
        KlineDivide klineDivide = klineDivideMapper.selectLatest(global_symbol);
        JLabel closeDesc = new JLabel("当前价格");
        JLabel bidDesc = new JLabel("买1价");
        JLabel bidQuanDesc = new JLabel("买1量");
        JLabel askDesc = new JLabel("卖1价");
        JLabel askQuanDesc = new JLabel("卖1量");


        jLabel1 = new JLabel(""+klineDivide.getClose());
        jLabel2 = new JLabel(""+klineDivide.getBid().get(0));
        jLabel3 = new JLabel(""+klineDivide.getBid().get(1));
        jLabel4 = new JLabel(""+klineDivide.getAsk().get(0));
        jLabel5 = new JLabel(""+klineDivide.getAsk().get(1));
        jPanel.add(closeDesc);
        jPanel.add(bidDesc);
        jPanel.add(bidQuanDesc);
        jPanel.add(askDesc);
        jPanel.add(askQuanDesc);

        jPanel.add(jLabel1);
        jPanel.add(jLabel2);
        jPanel.add(jLabel3);
        jPanel.add(jLabel4);
        jPanel.add(jLabel5);
        return jPanel;
    }


   /* private JFreeChart createCombinedChart() {
        Map m = createDatasetMap();//从数据对象里取出各种类型的对象，主要是用来表示均线的时间线(IntervalXYDataset)对象和用来表示阴阳线和成交量的蜡烛图对象(OHLCDataset)
        IntervalXYDataset avg_line5 = (IntervalXYDataset) m.get("avg_line5");
        IntervalXYDataset avg_line10 = (IntervalXYDataset) m.get("avg_line10");
        IntervalXYDataset avg_line20 = (IntervalXYDataset) m.get("avg_line20");
        IntervalXYDataset avg_line60 = (IntervalXYDataset) m.get("avg_line60");
        IntervalXYDataset vol_avg_line5 = (IntervalXYDataset) m
                .get("vol_avg_line5");
        IntervalXYDataset vol_avg_line10 = (IntervalXYDataset) m
                .get("vol_avg_line10");
        OHLCDataset k_line = (OHLCDataset) m.get("k_line");
        OHLCDataset vol = (OHLCDataset) m.get("vol");
        String stock_name = (String) m.get("stock_name");
//设置若干个时间线的Render，目的是用来让几条均线显示不同的颜色，和为时间线加上鼠标提示
        XYLineAndShapeRenderer xyLineRender = new XYLineAndShapeRenderer(true,
                false);
        xyLineRender.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                "{0}: ({1}, {2})", new SimpleDateFormat("yyyy-MM-dd"),
                new DecimalFormat("0.00")));
        xyLineRender.setSeriesPaint(0, Color.red);
        XYLineAndShapeRenderer xyLineRender1 = new XYLineAndShapeRenderer(true,
                false);
        xyLineRender1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                "{0}: ({1}, {2})", new SimpleDateFormat("yyyy-MM-dd"),

                new DecimalFormat("0.00")));
        xyLineRender1.setSeriesPaint(0, Color.BLACK);
        XYLineAndShapeRenderer xyLineRender2 = new XYLineAndShapeRenderer(true,
                false);
        xyLineRender1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                "{0}: ({1}, {2})", new SimpleDateFormat("yyyy-MM-dd"),
                new DecimalFormat("0.00")));
        xyLineRender1.setSeriesPaint(0, Color.blue);

        XYLineAndShapeRenderer xyLineRender3 = new XYLineAndShapeRenderer(true,
                false);
        xyLineRender1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                "{0}: ({1}, {2})", new SimpleDateFormat("yyyy-MM-dd"),
                new DecimalFormat("0.00")));
        xyLineRender1.setSeriesPaint(0, Color.darkGray);
    }*/


    private void oneday(int p,OHLCSeries series,TimeSeries series2,Day today, Kline kline,int eDay,int eMonth,int eYear){
        if(p>=0){
            p--;
            series.add(today, kline.getOpen(), kline.getHigh(), kline.getLow(), kline.getClose());
            series2.add(today, kline.getAmount());

            eDay--;
            if (eDay<=0){
                   /* Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date(eYear,eMonth-1,2));
                    int monthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);*/

                eDay = getDaysByYearMonth(eYear, eMonth - 1);
                eMonth --;
            }
            if (eMonth<=0){
                eMonth =12;
                eYear --;
            }
            today = new Day(eDay,eMonth,eYear);
        }
    }


    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }




    public static void main(String[] args) {

    }


}
