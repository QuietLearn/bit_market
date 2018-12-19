package cn.stylefeng.guns.huobi.util.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeSeriesDemo1 extends JFrame implements Runnable, ActionListener {
    //时序图数据集
    private TimeSeries timeseries;
    //Value坐标轴初始值
    private double lastValue;
    static Class class$org$jfree$data$time$Millisecond;
    static Thread thread1;

    public static void main(String[] args){
        TimeSeriesDemo1 TimeSeriesDemo1 = new TimeSeriesDemo1();
        TimeSeriesDemo1.pack();
        RefineryUtilities.centerFrameOnScreen(TimeSeriesDemo1);
        TimeSeriesDemo1.setVisible(true);
        startThread();
    }

    public void run(){
        while(true){
            try{
                //根据实际需要在此处加入需要执行的代码
                double d = 0.9D + 0.2D * Math.random();
                lastValue = lastValue * d;
                Millisecond millisecond = new Millisecond();
                System.out.println("Now=" + millisecond.toString());
                timeseries.add(millisecond, lastValue);
                Thread.sleep(300);
            }catch(InterruptedException e){}
        }
    }

    public static void startThread(){
        thread1.start();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("EXIT")){
            thread1.destroy();
            System.exit(0);
        }
    }


    public TimeSeriesDemo1(){
        //super(new BorderLayout());
        thread1 = new Thread(this);
        lastValue = 100D;
        //创建时序图对象
        //TimeSeriesDemo1.class$org$jfree$data$time$Millisecond != null ? TimeSeriesDemo1.class$org$jfree$data$time$Millisecond : (TimeSeriesDemo1.class$org$jfree$data$time$Millisecond = TimeSeriesDemo1.getClass("org.jfree.data.time.Millisecond")
        timeseries = new TimeSeries("Random Data");
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeseries);
        //创建图表面板
        ChartPanel chartpanel = new ChartPanel(createChart(timeseriescollection));
        chartpanel.setPreferredSize(new Dimension(500,270));

        JPanel jpanel = new JPanel();
        jpanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));//边距为4
        JButton jbutton = new JButton("退出");
        jbutton.setActionCommand("EXIT");
        jbutton.addActionListener(this);
        jpanel.add(jbutton);

        getContentPane().add(chartpanel);
        getContentPane().add(jpanel,"South");
    }

    private JFreeChart createChart(XYDataset xydataset){
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("时序图例子","时间","温度值",xydataset,true,true,false);
        XYPlot xyplot = jfreechart.getXYPlot();
        //纵坐标设定
        ValueAxis valueaxis = xyplot.getDomainAxis();
        valueaxis.setAutoRange(true);
        valueaxis.setFixedAutoRange(60000D);

        valueaxis = xyplot.getRangeAxis();
        valueaxis.setRange(0.0D,200D);

        return jfreechart;
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
