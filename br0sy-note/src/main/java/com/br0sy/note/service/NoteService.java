package com.br0sy.note.service;

import cn.hutool.core.util.StrUtil;
import com.br0sy.note.dao.NoteDao;
import com.br0sy.note.dao.NoteTypeDao;
import com.br0sy.note.po.Note;
import com.br0sy.note.po.NoteType;
import com.br0sy.note.vo.ResultInfo;

import java.util.List;

public class NoteService {

    private NoteDao noteDao = new NoteDao();

    /**
     * 添加云记
     * @param note
     * @return
     */
    public ResultInfo<Note> addPageContent(Note note) {
        ResultInfo<Note> noteResultInfo = new ResultInfo<>();
        // 后端失败验证
        if (note.getTypeId() == -1) {
            noteResultInfo.setCode(0);
            noteResultInfo.setMsg("请选择类别！");
            noteResultInfo.setResult(note);
            return noteResultInfo;
        }
        if (StrUtil.isBlank(note.getTitle())) {
            noteResultInfo.setCode(0);
            noteResultInfo.setMsg("标题不能为空！");
            noteResultInfo.setResult(note);
            return noteResultInfo;
        }
        if (StrUtil.isBlank(note.getContent())) {
            noteResultInfo.setCode(0);
            noteResultInfo.setMsg("内容不能为空！");
            noteResultInfo.setResult(note);
            return noteResultInfo;
        }

        int row = 0;
        row = noteDao.addPageContent(note);
        if (row > 0) {
            // 保存成功
            noteResultInfo.setCode(1);
        } else {
            // 保存失败
            noteResultInfo.setCode(0);
            noteResultInfo.setMsg("服务端发生了一些未知的错误...");
            noteResultInfo.setResult(note);
        }
        return noteResultInfo;
    }

    /**
     * 获取云记列表
     * @param userId
     * @return
     */
    public List<Note> getListNotes(Integer userId) {
        List<Note> notes = null;
        // 调用Dao层对象，返回notes列表
        notes = noteDao.getListNotes(userId);
        // note类型的绑定和类型时间的清洗
        NoteTypeDao noteTypeDao = new NoteTypeDao();
        for (Note note : notes) {
            NoteType noteType = noteTypeDao.queryNoteTypeByTypeID(note.getTypeId());
            note.setTypeName(noteType.getTypeName());
            // 时间清洗
            String notePubTime = String.valueOf(note.getPubTime());
            String noteTime = notePubTime.substring(0, 4) + "/" + notePubTime.substring(5, 7) + "/" + notePubTime.substring(8,10);
            note.setNoteTime(noteTime);
        }
        return notes;
    }

    /**
     *  用 noteId 查询 Note
     * @param noteId
     */
    public Note inquireNoteWithNoteId(String noteId) {
        // 调用 Dao 层对象方法，进行查询
        Note note = noteDao.inquireNoteWithNoteId(noteId);
        return note;
    }

    /**
     * 删除云记
     * @param noteId
     * @return
     */
    public int deleteNote(String noteId) {
        // 直接调用数据库函数进行删除
        int row = noteDao.deleteNote(noteId);
        return row;
    }
}
