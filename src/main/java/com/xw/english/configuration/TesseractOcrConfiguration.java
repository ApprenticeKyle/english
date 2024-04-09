package com.xw.english.configuration;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractOcrConfiguration {

    @Value("${tess4j.datapath}")
    private String dataPath;

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        // 设置训练数据文件夹路径
        tesseract.setDatapath(dataPath);
        tesseract.setTessVariable("user_defined_dpi", "300");
        // 设置为中文简体
        tesseract.setLanguage("chi_sim+eng");
        return tesseract;
    }

}
