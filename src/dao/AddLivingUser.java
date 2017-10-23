package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddLivingUser {
    /*
     * 添加在线用户
     */
    public static void addLivingUser(String userName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "insert into livinguser (username) value(?)";  // 插入语句
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ps.executeUpdate();     // 执行更新

        ps.close();
        con.close();
    }
}
