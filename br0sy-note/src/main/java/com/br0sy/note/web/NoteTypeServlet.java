package com.br0sy.note.web;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.br0sy.note.po.NoteType;
import com.br0sy.note.po.User;
import com.br0sy.note.service.NoteTypeService;
import com.br0sy.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/noteType")
public class NoteTypeServlet extends HttpServlet {

    private NoteTypeService noteTypeService = new NoteTypeService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        // 获取参数
        String actionName = request.getParameter("actionName");
        // 判断参数
        if ("ManagePage".equals(actionName)) {
            // 加载资源
            ManagePage(request, response);
        } else if ("addTypeOrExType".equals(actionName)) {
            // 添加类型
            addOrExType(request, response);
        } else if ("deleteType".equals(actionName)) {
            // 删除类型
            deleteType(request, response);
        }
    }

    /**
     * 删除类型
     * @param request
     * @param response
     */
    private void deleteType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取要删除的typeID
        String typeId = request.getParameter("typeId");
        // 调用service层的函数
        noteTypeService.deleteType(typeId);
        // 请求转发
        request.getRequestDispatcher("noteType?actionName=ManagePage").forward(request, response);

    }

    /**
     * 添加类型
     * @param request
     * @param response
     */
    private void addOrExType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 获取参数
        String typeName = request.getParameter("typeName");
        User user = (User) request.getSession().getAttribute("user");
        Integer typeId = Integer.valueOf(request.getParameter("typeId"));
        // 得到提示信息
        ResultInfo<NoteType> resultInfo = noteTypeService.addTypeOrExTypeMsg(typeName, user.getUserId(), typeId);
        response.setContentType("application/json;charset=utf-8");
        // 返回Json数据供前端Ajax接收，这里转发Ajax请求是无法修改内部的data的，所以单纯存数据返回前端响应就行了
        // 缓存Json对象
        JSONObject object = new JSONObject();
        object.putOnce("code", resultInfo.getCode());
        object.putOnce("msg", resultInfo.getMsg());
        if(resultInfo.getResult() != null) {
            object.putOnce("typeId", resultInfo.getResult().getTypeId());
        }
        // 实例化一个Json对象 将resultInfo对象加入到其中，result数据就包含了对象的字符串型了
        JSONObject result = new JSONObject();
        result.putOnce("resultInfo", object);
        // 再将数据发送给前端的Ajax接收
        response.getWriter().append(result.toString());
    }


    /**
     *  跳转到类别管理页面，并加载类别
     * @param request
     * @param response
     */
    private void ManagePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 高亮
        request.setAttribute("menu_page", "ManagePage");
        // 设置前台
        request.setAttribute("changePage", "centerPage/ManagePage.jsp");
        // 获取参数
        User user = (User) request.getSession().getAttribute("user");
        // 查询参数
        List<NoteType> noteTypes = noteTypeService.getTypes(user.getUserId());
        // 设置参数
        request.setAttribute("noteTypes", noteTypes);
        // 请求转发
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
