package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifyFriendName {
    /*
     * 备注好友名称
     */
    public static void modifyFriendName(String userName, String subName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection();   // 获取连接

        String sql = "update friends set subname = ? where username = ?"; // 更新语句
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, subName);   // 填补参数
        ps.setString(2, userName);
        ps.executeUpdate();    // 执行更新操作

        System.out.println("修改成功");

        ps.close();
        con.close();
    }
}
