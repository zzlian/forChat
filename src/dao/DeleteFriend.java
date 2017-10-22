package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFriend {
    /*
     * 删除好友
     */
    public static void deleteFriend(String userName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "delete from friends where username = ?";  // 删除语句
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ps.executeUpdate();     // 执行删除

        System.out.println("删除成功");

        ps.close();
        con.close();
    }
}
