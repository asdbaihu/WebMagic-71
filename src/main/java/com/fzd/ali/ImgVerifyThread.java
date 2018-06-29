package com.fzd.ali;

import com.alibaba.fastjson.JSON;
import com.fzd.ali.po.ImgVerifyResult;
import com.fzd.httpclient4_5.HttpUtil;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by FZD on 2018/6/19.
 * Description:
 */
public class ImgVerifyThread implements  Runnable{
    private int index = 0;
    private String name;

    public ImgVerifyThread(int index, String name){
        this.index = index;
        this.name = name;
    }

    @Override
    public void run() {
        TestImgThread testImgThread = new TestImgThread();
        testImgThread.test();
    }

    public static void main(String... args) throws InterruptedException {
        int[] arr = new int[]{0,1};
        //创建固定大小的线程池
        for (int i:arr) {
            //执行线程
            TestImgThread testImgThread = new TestImgThread();
            testImgThread.test();
        }
    }
}
