package com.xw.english.dao;

import com.xw.english.entity.Word;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EnglishDao {

    @Resource
    private JdbcTemplate jdbcTemplate;


    public List<Word> list() {
        String sql = "select * from word";
        return jdbcTemplate.queryForList(sql, Word.class);
    }
}
