/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2022-09-08 12:53:49 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.centerPage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class personalCenter_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<div class=\"content\">\r\n");
      out.write("    <div class=\"pcImgAndWords\">\r\n");
      out.write("        <img src=\"static/img/personalCenterPNG.png\">\r\n");
      out.write("        <span>个人中心</span>\r\n");
      out.write("    </div>\r\n");
      out.write("    <hr>\r\n");
      out.write("    <form action=\"userLoginIn\" method=\"POST\" class=\"exMsg\" enctype=\"multipart/form-data\">\r\n");
      out.write("\r\n");
      out.write("        <input type=\"hidden\" name=\"actionName\" value=\"updateUser\" id=\"actionName\">\r\n");
      out.write("        <span>昵称:</span>\r\n");
      out.write("        <input type=\"text\" class=\"nickName\" name=\"nickName\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.nick}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\"><br />\r\n");
      out.write("        <span>心情:</span>\r\n");
      out.write("        <textarea name=\"mood\" id=\"mood\" cols=\"30\" rows=\"10\" placeholder=\"无\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.mood}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</textarea><br>\r\n");
      out.write("        <span>上传头像:</span>\r\n");
      out.write("        <input type=\"file\" class=\"headImg\" name=\"headImg\"><br>\r\n");
      out.write("    </form>\r\n");
      out.write("    <span id=\"Info1\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${resultInfo.msg}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</span>\r\n");
      out.write("    <button onclick=\"exMsg()\" id=\"exButton\">修改</button>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    // 如果鼠标移开了nickname的输入框（也就是失焦了）\r\n");
      out.write("    $(\".nickName\").blur(function () {\r\n");
      out.write("       var nickName = $(\".nickName\").val();\r\n");
      out.write("       if (isEmpty(nickName)) {\r\n");
      out.write("           // 提示用户\r\n");
      out.write("           $(\"#Info1\").html(\"昵称不能为空！\");\r\n");
      out.write("           // 禁用按钮\r\n");
      out.write("           $(\"#exButton\").prop(\"disabled\", true);\r\n");
      out.write("           return;\r\n");
      out.write("       }\r\n");
      out.write("\r\n");
      out.write("        // 判断昵称是否做出了修改\r\n");
      out.write("        // 从session作用域中获取用户昵称\r\n");
      out.write("        var nick = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.nick}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("        // 如果昵称没改\r\n");
      out.write("        if (nickName === nick) {\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        // 如果昵称做了修改(利用Ajax进行请求)\r\n");
      out.write("        $.ajax({\r\n");
      out.write("            type:\"get\",\r\n");
      out.write("            url:\"userLoginIn\",\r\n");
      out.write("            data:{\r\n");
      out.write("                actionName:\"nickCheck\",\r\n");
      out.write("                nick:nickName\r\n");
      out.write("            },\r\n");
      out.write("            success:function (result) {\r\n");
      out.write("                if (result == 1) {\r\n");
      out.write("                    // 如果可用，清空提示信息，并启动按钮\r\n");
      out.write("                    $(\"#Info1\").html(\"\");\r\n");
      out.write("                    $(\"#exButton\").prop(\"disabled\", false);\r\n");
      out.write("                } else if (result == 0) {\r\n");
      out.write("                    $(\"#Info1\").html(\"昵称不能为空！\");\r\n");
      out.write("                    $(\"#exButton\").prop(\"disabled\", true);\r\n");
      out.write("                } else if (result == 2) {\r\n");
      out.write("                    // 如果不可用,提示用户，禁用按钮\r\n");
      out.write("                    $(\"#Info1\").html(\"昵称已存在！\");\r\n");
      out.write("                    $(\"#exButton\").prop(\"disabled\", true);\r\n");
      out.write("                }\r\n");
      out.write("            },\r\n");
      out.write("            error:function () {\r\n");
      out.write("                $(\"#Info1\").html(\"请求错误。\");\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    }).focus(function () {\r\n");
      out.write("        // 清空提示信息\r\n");
      out.write("        $(\"#Info1\").html(\"\");\r\n");
      out.write("        // 启用按钮\r\n");
      out.write("        $(\"#exButton\").prop(\"disabled\", false);\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    if ('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${resultInfo.code}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("' == 1) {\r\n");
      out.write("        alert(\"修改成功！\");\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("     * 用来对页面进行判断的js文件\r\n");
      out.write("     */\r\n");
      out.write("\r\n");
      out.write("    function exMsg() {\r\n");
      out.write("        // 前端再校验\r\n");
      out.write("        var nickName = $(\".nickName\").val();\r\n");
      out.write("        var moodValue = $(\"#mood\").val();\r\n");
      out.write("\r\n");
      out.write("        // 判断昵称是否做出了修改\r\n");
      out.write("        // 从session作用域中获取用户昵称\r\n");
      out.write("        var nick = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.nick}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("        var mood = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.mood}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("        // 如果昵称没改\r\n");
      out.write("        if (nickName === nick && moodValue === mood) {\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("        // 昵称是否为空\r\n");
      out.write("        if (isEmpty(nickName)) {\r\n");
      out.write("            $(\"#Info1\").html(\"昵称不能为空！\");\r\n");
      out.write("            // return;\r\n");
      out.write("        }\r\n");
      out.write("        $(\".exMsg\").submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("</script>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}