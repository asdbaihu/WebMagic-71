package com.fzd.img;


import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by FZD on 2018/6/11.
 * Description:
 */
public class ImgHandler {
    public static void getData(String path){
        try{
            BufferedImage bimg = ImageIO.read(new File(path));
            int [][] data = new int[bimg.getWidth()][bimg.getHeight()];
            //方式一：通过getRGB()方式获得像素矩阵
            //此方式为沿Height方向扫描
            for(int i=0;i<bimg.getWidth();i++){
                for(int j=0;j<bimg.getHeight();j++){
                    data[i][j]=bimg.getRGB(i,j);
                    //输出一列数据比对
                    if(i==0)
                        System.out.printf("%x\t",data[i][j]);
                }
            }
            Raster raster = bimg.getData();
            System.out.println("");
            int [] temp = new int[raster.getWidth()*raster.getHeight()*raster.getNumBands()];
            //方式二：通过getPixels()方式获得像素矩阵
            //此方式为沿Width方向扫描
            int [] pixels  = raster.getPixels(0,0,raster.getWidth(),raster.getHeight(),temp);
            for (int i=0;i<pixels.length;) {
                //输出一列数据比对
                if((i%raster.getWidth()*raster.getNumBands())==0)
                    System.out.printf("ff%x%x%x\t",pixels[i],pixels[i+1],pixels[i+2]);
                i+=3;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static int[][][] getData2(String base64Str){
        BASE64Decoder decoder = new BASE64Decoder();

        int rgb[][][]  = null;
        try {
            byte[] imgBytes = decoder.decodeBuffer(base64Str);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imgBytes);
            BufferedImage bimg = ImageIO.read(byteArrayInputStream);
            Raster raster = bimg.getData();
            int [] temp = new int[raster.getWidth()*raster.getHeight()*raster.getNumBands()];
            //方式二：通过getPixels()方式获得像素矩阵
            //此方式为沿Width方向扫描
            int [] pixels  = raster.getPixels(0,0,raster.getWidth(),raster.getHeight(),temp);
            rgb = new int[raster.getHeight()][raster.getWidth()][3];
            for (int i = 0; i < raster.getHeight(); i++) {
                for (int j = 0; j < raster.getWidth(); j++) {
                    rgb[i][j][0] = pixels[i * raster.getHeight() + j * 3];
                    rgb[i][j][1] = pixels[i * raster.getHeight() + j * 3 + 1];
                    rgb[i][j][2] = pixels[i * raster.getHeight() + j * 3 + 2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rgb;
    }

    public static void main(String [] args){
        String filePath = "C:\\Users\\SRKJ\\Desktop\\1836.jpg";
        String imgBase64 = Image.getImageStr(filePath);
        System.out.println(imgBase64);
        getData2(imgBase64);
    }
}
