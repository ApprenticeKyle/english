package com.xw.english;

import com.xw.english.entity.Record;
import com.xw.english.entity.Word;
import com.xw.english.facade.EnglishFacade;
import com.xw.english.facade.OcrFacade;
import com.xw.english.util.Util;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class EnglishApplicationTests {

    static final Logger log = LoggerFactory.getLogger(EnglishApplicationTests.class);

    @Autowired
    private EnglishFacade englishFacade;

    @Autowired
    private OcrFacade ocrFacade;


    public static void main(String[] args) {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("[M月d日][MM月d日][MM月dd日][M月dd日]").parseDefaulting(ChronoField.YEAR, 2024).toFormatter();

        LocalDate ld2 = LocalDate.parse("10月19日", formatter);
        System.out.println(ld2);
    }

    @Test
    public void testCorrectWord() {
        List<Word> list = englishFacade.list();
        for (Word word : list) {
            String content = word.getContent();
            if (!Util.isEnglishWord(content)) {
                System.out.println(content);
            }
        }
    }

    @Test
    public void testOcr() throws TesseractException, IOException {
        File dir = new File("C:\\Users\\w00030099\\Desktop\\words records");
        File[] files = dir.listFiles();
        for (File file : files) {
            MultipartFile multipartFile = new MockMultipartFile("file", Files.newInputStream(file.toPath()));
            String result = ocrFacade.recognizeText(multipartFile);
            String[] split = result.split("\n");
            boolean start = false;
            String localDate = null;
            List<Record> records = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (!start && "学习计划 已完成".equals(s.trim())) {
                    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("[M月d日][MM月d日][MM月dd日][M月dd日]").parseDefaulting(ChronoField.YEAR, 2024).toFormatter();
                    String dateString = split[i - 1];
                    try {
                        localDate = LocalDate.parse(dateString, formatter).toString();
                    } catch (Exception e) {
                        log.error("parseError:{}", dateString);
                        localDate = dateString;
                    }
                    start = true;
                    continue;
                }
                if (start) {
                    String content = s.split(" ")[0].toLowerCase(Locale.ROOT);
                    boolean englishWord = Util.isEnglishWord(content);
                    if (!englishWord) {
                        log.warn("不是英文单词:{}", content);
                        content = Util.correctWord(content);
                        log.warn("修正后:{}", content);
                        if (Util.isEnglishWord(content)) {
                            log.error("修正后仍然不是英文单词:{}", content);
                        }
                    }
                    Word word = new Word(content.toLowerCase(Locale.ROOT));
                    Word exist = englishFacade.getByContent(content);
                    if (exist == null) {
                        englishFacade.addWord(word);
                    }
                    int wordId = exist != null ? exist.getId() : word.getId();
                    records.add(new Record(wordId, localDate));
                }
            }
            englishFacade.addRecordBatch(records);
        }
    }

}
