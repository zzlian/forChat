package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsLivingUser {
    /*
     * 判断用户是否已经登录
     */
    public static boolean isLivingUser(String userName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "select * from livinguser where username = ?";  // 查询语句
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();     // 执行查询

        boolean is = rs.next(); // 判断是否有查询结果
        return is;
    }
}
