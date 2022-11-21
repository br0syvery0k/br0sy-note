package com.br0sy.note.web;

import com.br0sy.note.po.Note;
import com.br0sy.note.po.User;
import com.br0sy.note.service.NoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 这个servlet的作用主要是为了数据传递，因为response重定向会清除原先的request对象，所以request.set会小消失
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // 获取参数
        User user = (User) request.getSession().getAttribute("user");

        // 设置主页高亮
        request.setAttribute("menu_page", "mainPage");

        // 将user对象对应的云记取出来
        NoteService noteService = new NoteService();
        List<Note> notes = noteService.getListNotes(user.getUserId());
        // 设置主页数据
        request.setAttribute("notes", notes);
        request.setAttribute("nowPageNum", "1");

        // 请求转发
        request.getRequestDispatcher("page").forward(request, response);

    }

}
