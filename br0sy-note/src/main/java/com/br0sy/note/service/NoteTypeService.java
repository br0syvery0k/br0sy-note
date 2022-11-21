package com.br0sy.note.service;

import com.br0sy.note.dao.BaseDao;
import com.br0sy.note.dao.NoteTypeDao;
import com.br0sy.note.po.NoteType;
import com.br0sy.note.vo.ResultInfo;

import java.util.List;

public class NoteTypeService {

    //实例化数据库对象
    private NoteTypeDao noteTypeDao = new NoteTypeDao();

    /**
     * 这里好像就是单纯从后台取数据，没有什么需要逻辑判断的地方
     * @param userId
     * @return
     */
    public List<NoteType> getTypes(Integer userId) {
        List<NoteType> noteTypes = null;
        // 直接查询
        noteTypes = noteTypeDao.queryNoteTypesByUserId(userId);
        return noteTypes;
    }

    /**
     * 获取添加类型或修改类型的信息，判断类型名称的唯一性
     * @param typeName
     * @return
     */
    public ResultInfo<NoteType> addTypeOrExTypeMsg(String typeName, Integer userId, Integer typeId) {
        ResultInfo<NoteType> resultInfo = new ResultInfo<>();
        resultInfo.setResult(null);
        // 查询
        NoteType noteType = noteTypeDao.queryNoteTypeByTypeNameAndUserId(typeName, userId);
        // 判重
        if(noteType != null) {
            resultInfo.setCode(0);
            resultInfo.setMsg("类型昵称已存在");
            return resultInfo;
        } else {
            // typeId == 0 时是添加类型，否则是修改类型
            // resultInfo.setCode(0)时是失败，1是添加类型，2是修改类型
            if (typeId == 0) {
                // 将记录插入数据库中
                int row = 0;
                row = noteTypeDao.addNoteType(typeName, userId);
                // noteType传递
                noteType = noteTypeDao.queryNoteTypeByTypeNameAndUserId(typeName, userId);
                if (row > 0) {
                    resultInfo.setCode(1);
                    resultInfo.setResult(noteType);
                } else {
                    resultInfo.setCode(0);
                    resultInfo.setMsg("添加失败！");
                }
            } else {
                // 修改类型
                int row = 0;
                row = noteTypeDao.updateType(typeName, typeId);
                if (row > 0) {
                    resultInfo.setCode(2);
                } else {
                    resultInfo.setCode(0);
                    resultInfo.setMsg("添加失败！");
                }
            }

        }
        return resultInfo;
    }

    public void deleteType(String typeId) {
        // 只在前端做了校验，这地方有时间还是要重新改一下
        noteTypeDao.deleteType(typeId);
    }
}
