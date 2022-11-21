package com.br0sy.note.dao;

import com.br0sy.note.po.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {
    /**
     * 添加云记
     * @param note
     * @return
     */
    public int addPageContent(Note note) {
        int row = 0;
        String sql = "insert into tb_note (title,content,typeId,userId) values (?,?,?,?)";
        List<Object> params = new ArrayList<>();
        params.add(note.getTitle());
        params.add(note.getContent());
        params.add(note.getTypeId());
        params.add(note.getUserId());
        row = BaseDao.excuteUpdate(sql, params);
        return row;
    }

    /**
     * 获取云记集群对象，并返回
     * @param userId
     * @return
     */
    public List<Note> getListNotes(Integer userId) {
        List<Note> notes = null;
        String sql = "select noteId,title,typeId,pubTime from tb_note where userId=?";
        List<Object> params = new ArrayList<>();
        params.add(userId);
        notes = BaseDao.queryRows(sql, params, Note.class);
        return notes;
    }


    /**
     * 用 noteId 查询 Note
     * @param noteId
     * @return
     */
    public Note inquireNoteWithNoteId(String noteId) {
        int noteid = Integer.parseInt(noteId);
        Note note = null;
        String sql = "select title,content,pubTime from tb_note where noteId=?";
        List<Object> params = new ArrayList<>();
        params.add(noteid);
        note = (Note) BaseDao.queryRow(sql, params, Note.class);
        note.setNoteId(noteid);
        return note;
    }

    /**
     * 通过NoteId删除云记
     * @param noteId
     * @return
     */
    public int deleteNote(String noteId) {
        int noteid = Integer.parseInt(noteId);
        int row = 0;
        String sql = "delete from tb_note where noteId=?";
        List<Object> params = new ArrayList<>();
        params.add(noteid);
        row = BaseDao.excuteUpdate(sql, params);
        return row;
    }
}
