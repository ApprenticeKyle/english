package com.xw.english.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`record`")
public class Record {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private int wordId;

    private String time;

    public Record(int wordId, String time) {
        this.wordId = wordId;
        this.time = time;
    }

    public Record() {
    }
}
