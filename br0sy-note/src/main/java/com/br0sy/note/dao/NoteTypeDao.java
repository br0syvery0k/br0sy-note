package com.br0sy.note.dao;

import com.br0sy.note.po.NoteType;

import java.util.ArrayList;
import java.util.List;

import static com.br0sy.note.dao.BaseDao.*;

public class NoteTypeDao {

    /**
     * 通过用户名查询该用户创建的类型，并以列表形式返回
     * @param userId
     * @return
     */
    public List<NoteType> queryNoteTypesByUserId(Integer userId) {
        List<NoteType> list = null;
        String sql = "select typeId,typeName,userId from tb_note_type where userId=?";
        List<Object> params = new ArrayList<>();
        params.add(userId);
        list = queryRows(sql, params, NoteType.class);
        return list;
    }

    /**
     * 通过typeId进行查询
     * @param typeId
     * @return
     */
    public NoteType queryNoteTypeByTypeID(Integer typeId) {
        NoteType noteType = null;
        String sql = "select typeId,typeName,userId from tb_note_type where typeId=?";
        List<Object> params = new ArrayList<>();
        params.add(typeId);
        noteType = (NoteType) queryRow(sql, params, NoteType.class);
        return noteType;
    }

    /**
     * 通过 typeName 和 userId 进行查询
     * @param typeName
     * @return
     */
    public NoteType queryNoteTypeByTypeNameAndUserId(String typeName, Integer userId) {
        NoteType noteType = null;
        String sql = "select typeId,typeName,userId from tb_note_type where typeName=? and userId=?";
        List<Object> params = new ArrayList<>();
        params.add(typeName);
        params.add(userId);
        noteType = (NoteType) queryRow(sql, params, NoteType.class);
        return noteType;
    }

    /**
     * 添加NoteType
     * @param typeName
     * @param userId
     * @return
     */
    public int addNoteType(String typeName, Integer userId) {
        int row = 0;
        String sql = "insert into tb_note_type (typeName,userId) values (?,?);";
        List<Object> params = new ArrayList<>();
        params.add(typeName);
        params.add(userId);
        row = excuteUpdate(sql, params);
        return row;
    }

    /**
     * 更新类型
     * @param typeName
     * @param typeId
     * @return
     */
    public int updateType(String typeName, Integer typeId) {
        int row = 0;
        String sql = "update tb_note_type set typeName=? where typeId=?";
        List<Object> list = new ArrayList<>();
        list.add(typeName);
        list.add(typeId);
        row = BaseDao.excuteUpdate(sql, list);
        return  row;
    }

    /**
     * 删除类型
     * @param typeId
     */
    public void deleteType(String typeId) {
        String sql = "delete from tb_note_type where typeId=?";
        List<Object> params = new ArrayList<>();
        params.add(typeId);
        excuteUpdate(sql, params);
    }
}
