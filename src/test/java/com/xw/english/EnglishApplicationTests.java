package com.xw.english;

import com.xw.english.service.OcrService;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class EnglishApplicationTests {

    static final Logger log = LoggerFactory.getLogger(EnglishApplicationTests.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private OcrService ocrService;

    @Test
    public void test() throws Exception {
        // 1、首先创建数据表
//        String ddl = "CREATE TABLE `user` (\n" + "                id INTEGER PRIMARY KEY NOT NULL,\n" + "                name TEXT,\n" + "                create_at TEXT\n" + "            );";
//
//        this.jdbcTemplate.execute(ddl);
//
//        // 2、插入一条数据
//        int ret = this.jdbcTemplate.update("INSERT INTO `user` (`id`, `name`, `create_at`) VALUES (?, ?, ?);", new Object[]{1, "springdoc", LocalDateTime.now()});
//
//        log.info("插入数据：{}", ret);
//
//        // 3、检索一条数据
//        Map<String, Object> user = this.jdbcTemplate.queryForObject("SELECT * FROM `user` WHERE `id` = ?", new ColumnMapRowMapper(), 1L);
//
//        log.info("检索数据：{}", user);
    }

    public static void main(String[] args) {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("[M月d日][MM月d日][MM月dd日][M月dd日]")
                .parseDefaulting(ChronoField.YEAR, 2024)
                .toFormatter();

        LocalDate ld2 = LocalDate.parse("10月19日", formatter);
        System.out.println(ld2);
    }

    @Test
    public void testOcr() throws TesseractException, IOException {
        File dir = new File("C:\\Users\\w00030099\\Desktop\\words records");
        File[] files = dir.listFiles();
        for (File file : files) {
            MultipartFile multipartFile = new MockMultipartFile("file", new FileInputStream(file));
            String result = null;
            try {
                result = ocrService.recognizeText(multipartFile);
            } catch (TesseractException | IOException e) {
                throw new RuntimeException(e);
            }
            if (!result.contains("学习计划 已完成")) {
                System.out.println("数据有问题：" + result);
            }
            String[] split = result.split("\n");
            boolean start = false;
            List<String> words = new ArrayList<>();
            LocalDate localDate = null;
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (!start && s.contains("月") && s.contains("日")) {
                    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("[M月d日][MM月d日][MM月dd日][M月dd日]")
                            .parseDefaulting(ChronoField.YEAR, 2024)
                            .toFormatter();
                    try {
                        localDate = LocalDate.parse(s, formatter);
                    } catch (Exception e) {
                        System.out.println(s);
                    }
                    i++;
                    start = true;
                    continue;
                }
                if (start) {
                    words.add(s.split(" ")[0]);
                }
            }
            System.out.println(localDate + ":" + words.size());
            if (localDate == null) {
                System.out.println(Arrays.toString(split));
            }
        }
    }

}
