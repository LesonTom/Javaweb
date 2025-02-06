package com.leiting.oa.web.action;


import com.leiting.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        //获取部门编号
        String deptno = request.getParameter("deptno");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html lang=\"en\">");
        out.print("<head>");
        out.print("    <meta charset=\"UTF-8\">");
        out.print("    <title>修改部门</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<h1>修改部门</h1>");
        out.print("<hr>");
        out.print("<form action=\"" + contextPath + "/dept/update\" method=\"post\">");


        try {
            conn = DBUtil.getConnection();
            String sql = "select dname,location from dept where deptno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deptno);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String dname = rs.getString("dname");
                String location = rs.getString("location");
                out.print("    部门编号 <input type=\"text\" name=\"deptno\" value=\"" + deptno + "\" readonly><br>");
                out.print("    部门名称 <input type=\"text\" name=\"deptname\" value=\"" + dname + "\"><br>");
                out.print("    部门位置 <input type=\"text\" name=\"location\" value=\"" + location + "\"><br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.close(conn, pstmt, rs);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        out.print("    <input type=\"submit\" value=\"修改\"><br>");
        out.print("</form>");
        out.print("</body>");
        out.print("</html>");
    }
}
