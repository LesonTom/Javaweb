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

public class DeptDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取部门编号
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html lang=\"en\">");
        out.print("<head>");
        out.print("    <meta charset=\"UTF-8\">");
        out.print("    <title>部门详情</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<h1>部门详情</h1>");
        out.print("<hr>");


        String deptno = request.getParameter("deptno");

        //连接数据库，根据部门编号查询部门信息
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("select dname,location from dept where deptno=?");
            pstmt.setString(1, deptno);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String deptname = rs.getString("dname");
                String location = rs.getString("location");
                out.print("部门编号：" + deptno + "<br>");
                out.print("部门名称：" + deptname + "<br>");
                out.print("部门位置：" + location + "<br>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        out.print("</body>");
        out.print("</html>");
    }

}
