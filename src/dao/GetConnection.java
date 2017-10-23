package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    /*
     * 获取数据库连接
     */
    public static java.sql.Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");     // 加载数据库驱动
        String url = "jdbc:mysql://localhost:3306/chat";
        String userName = "root";
        String password = "13172872327";
        java.sql.Connection con = DriverManager.getConnection(url, userName, password);  // 获取数据库连接
        return con;
    }
}
