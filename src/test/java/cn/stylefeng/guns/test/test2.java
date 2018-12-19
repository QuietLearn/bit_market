package cn.stylefeng.guns.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test2 {

    @Test
    public void fun3(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(1545160200000l);
        System.out.println(format);
        String format2 = sdf.format(1545235199999l);
        System.out.println(format2);

        Date date = new Date(1545160200*1000l);
        System.out.println(date);

    }
}
