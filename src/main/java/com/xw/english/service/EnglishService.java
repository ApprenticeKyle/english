package com.xw.english.service;

import com.xw.english.dao.EnglishDao;
import com.xw.english.entity.Word;
import com.xw.english.util.Translator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnglishService {

    @Resource
    private EnglishDao englishDao;

    public List<Word> list() {
        return englishDao.list();
    }

    public String translate(String word) throws Exception {
        return Translator.translate("en", "zh_cn", word);
    }
}
