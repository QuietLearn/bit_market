package cn.stylefeng.guns.huobi.util.dateControls;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.*;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


public class JDatePickerTest {
    public static void main(String[] args) {

        JFrame f = new JFrame("LoL");
        f.setSize(400, 300);
        f.setLocation(200, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        JTextField jTextField = new JTextField();
        JPanel jPanel = new JPanel();
       /* final JDatePickerTest datepick;
        datepick = getDatePicker();
        f.add(datepick);

        JButton b = new JButton("获取时间");
        b.setBounds(137, 183, 100, 30);
        f.add(b);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(f, "获取控件中的日期：" + datepick.getValue());
                System.out.println(datepick.getValue());//这是一个java.util.Date对象
            }
        });*/
        JDatePickerTest datePickerTest = new JDatePickerTest();
        JDatePicker datePicker = datePickerTest.getDatePicker();
        jTextField.add((Component)datePicker);

        jPanel.add((Component)datePicker);
        f.add(jPanel);


    }

    public JDatePicker getDatePicker() {

       /* UtilDateModel model = new UtilDateModel();
        //model.setDate(20,04,2014);
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        f1.add(datePicker);*/
        UtilDateModel model = new UtilDateModel();
        //model.setDate(20,04,2014);
        // Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        // Don't know about the formatter, but there it is...
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        return datePicker;

    }

    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
