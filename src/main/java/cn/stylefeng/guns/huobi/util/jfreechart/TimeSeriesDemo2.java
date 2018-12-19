package cn.stylefeng.guns.huobi.util.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//导入java2d包
import java.awt.Dimension;
import java.awt.Color;


public class TimeSeriesDemo2 extends JFrame implements Runnable, ActionListener {
    //申明实时曲线对象
    private TimeSeries timeseries1;
    private TimeSeries timeseries2;

    //Value坐标轴初始值
    private double lastValue1,lastValue2;
    private double originalValue1,originalValue2;

    static Class class$org$jfree$data$time$Millisecond;
    static Thread thread1;

    public static void main(String[] args){
        TimeSeriesDemo2 TimeSeriesDemo2 = new TimeSeriesDemo2();
        TimeSeriesDemo2.pack();
        RefineryUtilities.centerFrameOnScreen(TimeSeriesDemo2);
        TimeSeriesDemo2.setVisible(true);
        startThread();
    }

    public void run(){
        while(true){
            try{
                //说明：在此处添加具体的业务数据

                //随机产生曲线1的数据
                double d1 = 2.0D * Math.random();
                lastValue1 = originalValue1 * d1;
                Millisecond millisecond1 = new Millisecond();
                System.out.println("Series1 Now=" + millisecond1.toString());
                timeseries1.add(millisecond1, lastValue1);
                //随机产生曲线2的数据
                double d2 = 2.0D * Math.random();
                lastValue2 = originalValue2 * d2;
                Millisecond millisecond2 = new Millisecond();
                System.out.println("Series2 Now=" + millisecond2.toString());
                timeseries2.add(millisecond2,lastValue2);

                Thread.sleep(500);
            }catch(InterruptedException e){}
        }
    }

    public static void startThread(){
        thread1.start();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("EXIT")){
            thread1.interrupt();
            System.exit(0);
        }
    }


    public TimeSeriesDemo2(){
        thread1 = new Thread(this);
        originalValue1 = 100D;
        originalValue2 = 100D;
        //创建时序图对象
        //,TimeSeriesDemo2.class$org$jfree$data$time$Millisecond != null ? TimeSeriesDemo2.class$org$jfree$data$time$Millisecond : (TimeSeriesDemo2.class$org$jfree$data$time$Millisecond = TimeSeriesDemo2.getClass("org.jfree.data.time.Millisecond"))
        //,timeseriesdemo2.class$org$jfree$data$time$millisecond != null ? timeseriesdemo2.class$org$jfree$data$time$millisecond : (timeseriesdemo2.class$org$jfree$data$time$millisecond = timeseriesdemo2.getclass("org.jfree.data.time.millisecond"))
        timeseries1 = new TimeSeries("热风温1");
        timeseries2 = new TimeSeries("热风温2");
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeseries1);
        TimeSeriesCollection timeseriescollection1 = new TimeSeriesCollection(timeseries2);

        //创建jfreechart对象
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("RTU温度模拟量实时曲线图","Time","Value",
                timeseriescollection,true,true,false);
        jfreechart.setBackgroundPaint(Color.white);

        //设定显示风格
        XYPlot xyplot = jfreechart.getXYPlot();
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
        ValueAxis valueaxis = xyplot.getDomainAxis();
        valueaxis.setAutoRange(true);
        valueaxis.setFixedAutoRange(60000D);
        //设定Value的范围
        valueaxis = xyplot.getRangeAxis();
        valueaxis.setRange(0.0D,200D);
        xyplot.setDataset(1, timeseriescollection1);
        xyplot.setRenderer(1,new DefaultXYItemRenderer());

        //创建图表面板
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        getContentPane().add(chartpanel);

        //根据需要添加操作按钮
        this.setTitle("RTU实时曲线");
        JPanel jpanel = new JPanel();
        jpanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));//边距为4
        JButton jbutton = new JButton("退出");
        jbutton.setActionCommand("EXIT");
        jbutton.addActionListener(this);
        jpanel.add(jbutton);
        getContentPane().add(jpanel,"South");
        chartpanel.setPreferredSize(new Dimension(500,270));
    }

    static Class getClass(String s){
        Class cls = null;
        try{
            cls = Class.forName(s);
        }catch(ClassNotFoundException cnfe){
            throw new NoClassDefFoundError(cnfe.getMessage());
        }
        return cls;
    }

}
