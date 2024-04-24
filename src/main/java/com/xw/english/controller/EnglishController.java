package com.xw.english.controller;

import com.xw.english.entity.Word;
import com.xw.english.result.Result;
import com.xw.english.facade.EnglishFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class EnglishController {

    @Resource
    private EnglishFacade englishFacade;

    @GetMapping("")
    public Result<List<Word>> hello() {
        return Result.success(englishFacade.list());
    }

    @GetMapping("/translate")
    public Result<String> translate(String word) throws Exception {
        return Result.success(englishFacade.translate(word));
    }
}
