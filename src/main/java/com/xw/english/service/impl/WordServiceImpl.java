package com.xw.english.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.english.mapper.WordMapper;
import com.xw.english.entity.Word;
import com.xw.english.service.WordService;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Override
    public Word getByContent(String content) {
        LambdaQueryWrapper<Word> wrapper = new QueryWrapper<Word>().lambda().eq(Word::getContent, content);
        return getOne(wrapper);
    }
}
