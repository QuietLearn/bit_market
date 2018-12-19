package cn.stylefeng.guns.test;

import cn.stylefeng.guns.huobi.util.KLineCombineChart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test1 {

    @Autowired
    private KLineCombineChart kLineCombineChart;

    @Test
    public void fun1(){
        System.out.println((int)(Math.random()*10));
    }

    @Test
    public void fun2(){
        kLineCombineChart.generateKline();
    }

    @Test
    public void fun3(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(1545187800000l);
        System.out.println(format);
        String format2 = sdf.format(1545235199999l);
        System.out.println(format2);
    }
}
