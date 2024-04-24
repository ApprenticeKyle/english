package com.xw.english.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.english.entity.Word;

public interface WordService extends IService<Word> {
    Word getByContent(String content);
}
