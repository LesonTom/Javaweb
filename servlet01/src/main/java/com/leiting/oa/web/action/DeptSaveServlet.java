package com.leiting.oa.web.action;

import com.leiting.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeptSaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门信息
        response.setCharacterEncoding("UTF-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("deptname");
        String loc = request.getParameter("location");

        //连接数据库执行insert语句
        Connection conn = null;
        PreparedStatement pst = null;
        int cnt = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into dept(deptno,dname,location) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, deptno);
            pst.setString(2, dname);
            pst.setString(3, loc);
            cnt = pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBUtil.close(conn, pst, null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //保存成功跳转到列表页面
        if (cnt > 0) {
            request.getRequestDispatcher("/dept/list").forward(request, response);
        } else {
            //保存失败跳转到错误页面
            request.getRequestDispatcher("/error.html").forward(request, response);
        }
    }
}
