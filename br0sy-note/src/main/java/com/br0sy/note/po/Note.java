package com.br0sy.note.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Note {

    private Integer noteId; // note编号
    private String title; // note标题
    private String content; // note内容
    private Integer typeId; // note类型编号
    private Date pubTime; // 发布日期
    private Integer userId; // note发布人编号

    // 不存入数据库，在Service层将typeID转换为对应的typeName,对应的pubTime清洗成noteTime
    private String typeName;
    private String noteTime;

}
