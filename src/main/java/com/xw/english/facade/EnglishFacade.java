package com.xw.english.facade;

import com.xw.english.entity.Record;
import com.xw.english.entity.Word;
import com.xw.english.service.RecordService;
import com.xw.english.service.WordService;
import com.xw.english.util.Translator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnglishFacade {

    @Resource
    private WordService wordService;

    @Resource
    private RecordService recordService;

    public List<Word> list() {
        return wordService.list();
    }

    public String translate(String word) throws Exception {
        return Translator.translate("en", "zh_cn", word);
    }

    public Word getByContent(String content) {
        return wordService.getByContent(content);
    }

    public void addWord(Word word) {
        wordService.save(word);
    }

    public void addRecordBatch(List<Record> list) {
        recordService.saveBatch(list);
    }
}
