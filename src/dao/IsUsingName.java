package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsUsingName {
    public static boolean isUsingName(String name) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取连接

        String sql = "select * from users where username = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();   // 数据库查询

        boolean is = rs.next();

        ps.close();
        con.close();

        return is;   // 若存在则返回true，否则返回false
    }
}
