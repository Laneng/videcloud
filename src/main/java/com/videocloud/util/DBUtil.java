package com.videocloud.util;
import com.videocloud.entity.Pwd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
/**
 * @Author fdy
 * @Date 2023/4/19 9:46 星期三
 * @Description
 */






public class DBUtil {
    // 数据库连接信息
    private static final String URL = "jdbc:mysql://10.70.60.67:3306/videocloud";
    private static final String USER = "java2205";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // 查询语句
    private static final String SQL_FIND_ALL_BY_ID = "SELECT * FROM pwd WHERE id = ?";

    /**
     * 根据id查询数据库pwd表的所有数据
     *
     * @param id
     * @return
     */
    public static Pwd findAllById(int id) {

        Pwd pwd = new Pwd();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(SQL_FIND_ALL_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int pwdId = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String keycode = rs.getString("keycode");
               pwd.setId(pwdId);
               pwd.setEmail(email);
               pwd.setPassword(password);
               pwd.setKeyCode(keycode);

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pwd;
    }



}


