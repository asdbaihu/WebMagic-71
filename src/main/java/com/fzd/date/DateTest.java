package com.fzd.date;

import java.util.Calendar;

/**
 * Created by SRKJ on 2017/7/31.
 */
public class DateTest {
    public static void main(String... args) {
        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        System.out.println(year);
        for(int i = 0; i < 10; i++) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMM");
            System.out.println("Today is:" + format.format(calendar.getTime()));
            calendar.add(Calendar.MONTH, -1);

        }
    }
}
