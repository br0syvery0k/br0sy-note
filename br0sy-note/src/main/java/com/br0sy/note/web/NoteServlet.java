package com.br0sy.note.web;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.br0sy.note.po.Note;
import com.br0sy.note.po.NoteType;
import com.br0sy.note.po.User;
import com.br0sy.note.service.NoteService;
import com.br0sy.note.service.NoteTypeService;
import com.br0sy.note.util.MyPageUtil;
import com.br0sy.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/note")
public class NoteServlet extends HttpServlet {

    private NoteService noteService = new NoteService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数
        String actionName = request.getParameter("actionName");
        // 判断操作
        if ("postPage".equals(actionName)) {
            // 发表云记界面
            postPage(request, response);
        } else if ("addPageContent".equals(actionName)) {
            // 发表云记
            addPageContent(request, response);
        } else if ("mainPage".equals(actionName)) {
            // 进入主页
            mainPage(request, response);
        } else if ("inquireNote".equals(actionName)) {
            // 根据noteId查询云记
            inquireNoteWithNoteId(request, response);
        } else if ("deleteNote".equals(actionName)) {
            // 删除云记
            deleteNote(request, response);
        }
    }

    /**
     * 删除云记
     * @param request
     * @param response
     */
    private void deleteNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数
        String noteId = request.getParameter("noteId");
        // 直接猛删
        int row = 0;
        row = noteService.deleteNote(noteId);
        if (row > 0) {
            // 删除成功
            request.setAttribute("isDelete", "1");
        } else {
            // 删除失败
            request.setAttribute("isDelete", "0");
        }
        // 请求转发
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }


    /**
     * 根据noteId查询云记
     * @param request
     * @param response
     */
    private void inquireNoteWithNoteId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置主页
        request.setAttribute("changePage", "centerPage/checkNotePage.jsp");
        request.setAttribute("menu_page", "mainPage");

        // 获取参数
        String noteId = request.getParameter("noteId");
        // 调用Service层进行查询
        Note note = noteService.inquireNoteWithNoteId(noteId);

        // 设置HTTP头
        request.setAttribute("note", note);
        // 请求转发
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * 进入主页
     * @param request
     * @param response
     */
    private void mainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置高亮
        request.setAttribute("menu_page", "mainPage");
        // 前台切换
        request.setAttribute("changePage", "centerPage/mainPage.jsp");
        // 获取参数
        String nowPageNum = request.getParameter("nowPageNum");
        if (StrUtil.isBlank(nowPageNum)) {
            nowPageNum = "1";
        }
        request.setAttribute("nowPageNum", nowPageNum);
        // 请求转发
        request.getRequestDispatcher("page").forward(request, response);
    }

    /**
     * 发表云记
     * @param request
     * @param response
     */
    private void addPageContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数
        String typeId = request.getParameter("LoadNoteType");
        Integer addNoteTypeId = -1;
        if (!StrUtil.isBlank(typeId)) {
            // 若typeId非空
            addNoteTypeId = Integer.parseInt(typeId);
        }
        String title = request.getParameter("noteName");
        String content = request.getParameter("content");
        User user = (User) request.getSession().getAttribute("user");
        // 实例化Note
        Note note = new Note();
        // 赋值
        note.setTypeId(addNoteTypeId);
        note.setTitle(title);
        note.setContent(content);
        note.setUserId(user.getUserId());
        // 传给Service
        ResultInfo<Note> noteResultInfo = noteService.addPageContent(note);
        // 放入HTTP头中
        request.setAttribute("noteResultInfo", noteResultInfo);
        // 请求转发
        request.getRequestDispatcher("note?actionName=postPage").forward(request, response);
    }

    /**
     *  跳转到发表云记页面
     * @param request
     * @param response
     */
    private void postPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 高亮
        request.setAttribute("menu_page", "postPage");
        // 设置前台
        request.setAttribute("changePage", "centerPage/postPage.jsp");
        // 获取noteTypes
        User user = (User) request.getSession().getAttribute("user");
        NoteTypeService noteTypeService = new NoteTypeService();
        List<NoteType> noteTypes = noteTypeService.getTypes(user.getUserId());
        // 将信息放入HTTP头中
        request.setAttribute("noteTypes", noteTypes);
        // 请求转发
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
