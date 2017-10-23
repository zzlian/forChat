package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetRecord {
    /*
     * 获取聊天记录信息
     */
    public static ArrayList<String> getRecord(String userName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "select * from messages where username = ?";  // 查询语句
        PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();

        rs.last();
        int count = rs.getRow();  // 聊天记录数
        rs.first();
        while(count > 50){  // 保持记录在50条，多的删除
            rs.next();
            rs.deleteRow();
            count --;
        }
        rs.close();

        rs = ps.executeQuery();
        ArrayList<String> records = new ArrayList<String>();
        while(rs.next()){
            records.add(rs.getString(3));
        }

        ps.close();
        con.close();

        return records;
    }
}
