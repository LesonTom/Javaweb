package com.leiting.oa.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.leiting.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeptDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据部门编号删除部门
        String deptNo = request.getParameter("deptno");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "delete from dept where deptno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deptNo);
            cnt = pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            try {
                DBUtil.close(conn, pstmt, rs);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (cnt == 1) {
            request.getRequestDispatcher("/dept/list").forward(request, response);
        } else {
            //失败
            request.getRequestDispatcher("/error.html").forward(request, response);
        }
    }

}
