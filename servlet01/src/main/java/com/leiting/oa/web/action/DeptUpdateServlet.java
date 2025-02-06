package com.leiting.oa.web.action;


import com.leiting.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String deptNo = request.getParameter("deptno");
        String deptName = request.getParameter("deptname");
        String location = request.getParameter("location");

        //连接数据库，执行更新语句
        Connection conn = null;
        PreparedStatement pstmt = null;
        int cnt = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "update dept set dname=?,location=? where deptno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deptName);
            pstmt.setString(2, location);
            pstmt.setString(3, deptNo);
            cnt = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.close(conn, pstmt, null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (cnt > 0) {
            request.getRequestDispatcher("/dept/list").forward(request, response);
        } else {
            request.getRequestDispatcher("/error.html").forward(request, response);
        }
    }
}
