package com.br0sy.note;

import com.br0sy.note.po.Note;
import com.br0sy.note.service.NoteService;
import com.br0sy.note.util.DBUtil;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Logger;

public class TestDB {


    // 使用日志工厂类，记录日志
//    private Logger logger = LoggerFactory.getLogger(TestDB.class);
    /**
     * 单元测试方法
     * 1、方法的返回值，建议使用void，一般没有返回值
     * 2、参数列表，建议空参，一般是没有参数
     * 3、方法上需要设置@Test注解
     * 4、每个方法都能独立运行
     */

    @Test
    public void textDB() {
        System.out.println(DBUtil.getConnection());
        // 使用日志
        // logger.info("获取数据库连接" + DBUtil.getConnection());
    }

    @Test
    public void testNoteDao() {
        NoteService noteService = new NoteService();
        List<Note> notes = noteService.getListNotes(0);
        for (int i=0;i<notes.size();i++) {
            System.out.println(notes.get(i).getTitle());
        }
    }

    @Test
    public void tbInsertNote() {
        //做你妈测试
    }

}
