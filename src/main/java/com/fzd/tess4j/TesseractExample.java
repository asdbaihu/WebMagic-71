package com.fzd.tess4j;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * Created by FZD on 2018/5/18.
 * Description:
 */
public class TesseractExample {
    public static void main(String[] args) {
        // ImageIO.scanForPlugins(); // for server environment
        File imageFile = new File("C:\\Users\\SRKJ\\IdeaProjects\\WebMagic\\src\\main\\java\\com\\fzd\\tess4j\\8672414605.jpg");
        ITesseract instance = new Tesseract(); // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
//         instance.setDatapath("<parentPath>"); // replace <parentPath> with path to parent directory of tessdata
         instance.setLanguage("chi_sim");

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}