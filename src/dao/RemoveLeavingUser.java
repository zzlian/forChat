package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveLeavingUser {
    /*
     * 移除下线的用户
     */
    public static void removeLeavingUser(String userName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "delete from livinguser where username = ?";  // 删除语句
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ps.executeUpdate();     // 执行更新

        ps.close();
        con.close();
    }
}
