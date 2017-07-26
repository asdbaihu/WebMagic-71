package com.fzd.string;

import java.security.Timestamp;
import java.util.regex.Pattern;

/**
 * Created by SRKJ on 2017/7/24.
 */
public class ExistsInString {

    public static void main(String... args){
        String str0 = "|456|";
        String str1 = "|444|";
        String str2 = "|111|222|333|444|555|666|777|888|999|000|";

        long loopCount = (long) 10e6;
        for(int i = 0; i <= 6; i++){
            long lastTime = System.currentTimeMillis();
            for(int j =0; j < loopCount; j++) {
                if (str2.contains(str0)) {
                }
            }
            long endTime = System.currentTimeMillis();
            long strConNoExists = endTime - lastTime;

            lastTime = System.currentTimeMillis();
            for(int j =0; j < loopCount; j++) {
                if (str2.contains(str1)) {
                }
            }
            endTime = System.currentTimeMillis();
            long strConExists = endTime - lastTime;

            lastTime = System.currentTimeMillis();
            for(int j =0; j < loopCount; j++) {
                if (str2.indexOf(str0) >= 0) {
                }
            }
            endTime = System.currentTimeMillis();
            long strInNoExists = endTime - lastTime;

            lastTime = System.currentTimeMillis();
            for(int j =0; j < loopCount; j++) {
                if (str2.indexOf(str1)>=0) {
                }
            }
            endTime = System.currentTimeMillis();
            long strInExists = endTime - lastTime;


//            Pattern pattern = Pattern.compile("[|]444[|]");
            lastTime = System.currentTimeMillis();
            for(int j =0; j < loopCount; j++) {
                if (Pattern.matches("[|]456[|]",str2)) {
                }
            }
            endTime = System.currentTimeMillis();
            long strRegNoExists = endTime - lastTime;

            lastTime = System.currentTimeMillis();
            for(int j =0; j < loopCount; j++) {
                if (Pattern.matches("[|]444[|]",str2)) {
                }
            }
            endTime = System.currentTimeMillis();
            long strRegExists = endTime - lastTime;
            System.out.println("no exists >>>");
            System.out.println("tsStrConNoExists = " + strConNoExists);
            System.out.println("tsStrIndNoExists = " + strInNoExists);
            System.out.println("tsStrRegNoExists = " + strRegNoExists);
            System.out.println("exists >>>");
            System.out.println("tsStrConExists = " + strConExists);
            System.out.println("tsStrIndExists = " + strInExists);
            System.out.println("tsStrRegExists = " + strRegExists);
        }


    }
}
