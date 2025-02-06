package com.leiting.oa.web.action;

import com.leiting.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeptListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, RuntimeException {
        String contextPath = req.getContextPath();//获取项目根路径

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html lang=\"en\">");
        out.print("<head>");
        out.print("    <meta charset=\"UTF-8\"/>");
        out.print("    <title>部门列表页面</title>");
        out.print("    <script type=\"text/javascript\">");
        out.print("        function del(dno) {");
        out.print("            if (window.confirm('删除不可恢复！')) {");
        out.print("                document.location.href = '/oa/dept/delete?deptno=' + dno");
        out.print("            }");
        out.print("        }");
        out.print("    </script>");
        out.print("</head>");
        out.print("");
        out.print("<body>");
        out.print("<h1 align='center'>部门列表</h1>");
        out.print("<div style=\"font-size:30px\" align=\"right\">主管：<span style=\"font-family:华文仿宋\">丁志安</span></div>");
        out.print("<hr>");
        out.print("<table border=\"1px\" align=\"center\" width=\"50%\">");
        out.print("    <tr>");
        out.print("        <th>序号</th>");
        out.print("        <th>部门名称</th>");
        out.print("        <th>部门名称</th>");
        out.print("        <th>操作</th>");
        out.print("    </tr>");

        //连接数据库，查询所有部门
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from dept";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int i = 1;
            while (rs.next()) {
                int dept_id = rs.getInt("deptno");
                String dept_name = rs.getString("dname");
                String location = rs.getString("location");
                out.print("    <tr>");
                out.print("        <td>" + (i++) + "</td>");
                out.print("        <td>" + dept_id + "</td>");
                out.print("        <td>" + dept_name + "</td>");
                out.print("        <td>");
                out.print("            <a href=\"javascript:void(0)\" onclick=\"del(" + dept_id + ")\">删除</a>");
                out.print("            <a href=\"" + contextPath + "/dept/edit?deptno=" + dept_id + "\">修改</a>");
                out.print("            <a href=\"" + contextPath + "/dept/detail?deptno=" + dept_id + "\">详情</a>");
                out.print("        </td>");
                out.print("    </tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.close(conn, stmt, rs);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        out.print("</table>");
        out.print("<hr>");
        out.print("");
        out.print("<a href=\"" + contextPath + "/add.html\">新增部门</a>");
        out.print("");
        out.print("</body>");
        out.print("</html>");

    }
}
