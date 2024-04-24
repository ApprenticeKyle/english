package com.xw.english.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("`word`")
@Data
public class Word {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String content;

    private String translation;


    public Word(String content) {
        this.content = content;
    }
}
