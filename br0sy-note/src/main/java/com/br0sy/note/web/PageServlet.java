package com.br0sy.note.web;

import cn.hutool.core.util.StrUtil;
import com.br0sy.note.po.Note;
import com.br0sy.note.po.User;
import com.br0sy.note.service.NoteService;
import com.br0sy.note.util.MyPageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/page")
public class PageServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NoteService noteService = new NoteService();
        // 获取参数
        // 获得User对象
        User user = (User) request.getSession().getAttribute("user");
        String nowPageNumStr = (String) request.getAttribute("nowPageNum");
        List<Note> allNotes = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        MyPageUtil myPageUtil = null;
        if (!StrUtil.isBlank(nowPageNumStr)) {
            int nowPageNum = Integer.parseInt(nowPageNumStr);
            // 调用noteService层函数获得notes对象
            allNotes = noteService.getListNotes(user.getUserId());
            // 获取参数
            myPageUtil = new MyPageUtil(allNotes.size());
            myPageUtil.calPreAndNextPageNum(nowPageNum);
            // 根据页数拆解allNotes
            int fromIndex = (myPageUtil.getNowPageNum()-1) * myPageUtil.getMaxArticleInOnePage();
            int toIndex = myPageUtil.getNowPageNum()* myPageUtil.getMaxArticleInOnePage();
            if (myPageUtil.getNowPageNum() == myPageUtil.getPageCount()) {
                toIndex = allNotes.size();
            }
            myPageUtil.setLastArticleNumber(toIndex);
            notes = allNotes.subList(fromIndex, toIndex);
        }
        // 设置HTTP内容
        request.setAttribute("notes", notes);
        request.setAttribute("myPageUtil", myPageUtil);
        // 请求转发
        request.getRequestDispatcher("index.jsp").forward(request, response);



    }




}
