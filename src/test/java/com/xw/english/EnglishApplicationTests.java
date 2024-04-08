package com.xw.english;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
class EnglishApplicationTests {

    static final Logger log = LoggerFactory.getLogger(EnglishApplicationTests.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() throws Exception {
        // 1、首先创建数据表
        String ddl = "CREATE TABLE `user` (\n" + "                id INTEGER PRIMARY KEY NOT NULL,\n" + "                name TEXT,\n" + "                create_at TEXT\n" + "            );";

        this.jdbcTemplate.execute(ddl);

        // 2、插入一条数据
        int ret = this.jdbcTemplate.update("INSERT INTO `user` (`id`, `name`, `create_at`) VALUES (?, ?, ?);", new Object[]{1, "springdoc", LocalDateTime.now()});

        log.info("插入数据：{}", ret);

        // 3、检索一条数据
        Map<String, Object> user = this.jdbcTemplate.queryForObject("SELECT * FROM `user` WHERE `id` = ?", new ColumnMapRowMapper(), 1L);

        log.info("检索数据：{}", user);
    }

}
