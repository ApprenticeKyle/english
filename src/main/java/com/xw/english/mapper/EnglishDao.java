//package com.xw.english.dao;
//
//import com.xw.english.entity.Record;
//import com.xw.english.entity.Word;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.sql.PreparedStatement;
//import java.sql.Statement;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class EnglishDao {
//
//    @Resource
//    private JdbcTemplate jdbcTemplate;
//
//
//    public List<Word> list() {
//        String sql = "select * from word";
//        return jdbcTemplate.queryForList(sql, Word.class);
//    }
//
//    public void insertWord(Word add) {
//        String sql = "insert into word(content,time) values(?,?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        Object[] args = new Object[]{add.getContent(), add.getTime()};
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            for (int i = 1; i < args.length + 1; i++) {
//                ps.setObject(i, args[i - 1]);
//            }
//            return ps;
//        }, keyHolder);
//        Number key = keyHolder.getKey();
//        if (key != null) {
//            add.setId(key.intValue());
//        }
//    }
//
//    public Word getByContent(String content) {
//        String sql = "select * from word where content = ?";
//        List<Word> words = jdbcTemplate.queryForList(sql, Word.class, content);
//        return CollectionUtils.isEmpty(words) ? null : words.get(0);
//    }
//
//    public void batchSaveRecord(List<Record> saveList) {
//        String sql = "insert into record(word_id,time) values(?,?)";
//        List<Object[]> args = saveList.stream().map(each -> new Object[]{each.getWordId(), each.getTime()}).collect(Collectors.toList());
//        jdbcTemplate.batchUpdate(sql, args);
//    }
//
//}
