package com.fzd.img;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by SRKJ on 2017/8/21.
 */
public class Image {
    public static void main(String[] args){
        System.out.println(getImageStr("C:\\Users\\SRKJ\\Desktop\\运营商.png"));
    }

    /**
     * 将图片文件转为字符串
     * @param imgFile
     * @return
     */
    public static String getImageStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //String imgFile = "d:\\111.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

}
