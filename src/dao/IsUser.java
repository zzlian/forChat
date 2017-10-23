package dao;

import java.sql.*;

public class IsUser {
    /*
     * 查询用户是否存在
     */
    public static boolean isUser(String userName, String password) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取连接

        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();   // 数据库查询

        boolean is = rs.next();

        ps.close();
        con.close();

        return is;   // 若存在则返回true，否则返回false
    }
}
