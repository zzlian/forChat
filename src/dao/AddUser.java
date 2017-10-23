package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUser {
    /*
     * 添加用户信息
     */
    public static void addUser(String userName, String password) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "insert into user (username,password) value(?,?)";  // 插入语句
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.executeUpdate();     // 执行更新

        ps.close();
        con.close();
    }
}
