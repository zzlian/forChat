package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetRecord {
    public static ArrayList<String> getRecord(String userName) throws SQLException, ClassNotFoundException {
        Connection con = GetConnection.getConnection(); // 获取数据库连接

        String sql = "selete * from aa where username = ?";  // 查询语句
        PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();

        rs.last();
        int count = rs.getRow();
        rs.first();
        while(count > 1){
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
        return records;
    }
}
