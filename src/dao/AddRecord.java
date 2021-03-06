package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRecord {
    /*
     * 添加聊天记录信息
     */
    public static void addRecord(String message, String userName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "insert into messages (username,message) value(?,?)";  // 插入语句
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, message);
        ps.executeUpdate();     // 执行更新

        ps.close();
        con.close();
    }
}
