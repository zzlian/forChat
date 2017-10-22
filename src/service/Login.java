package service;

import dao.IsExist;

import java.sql.SQLException;

public class Login {
    /*
     * 用户登录
     */
    public static void login(String userName, String password) throws SQLException, ClassNotFoundException {
        boolean isUser = IsExist.isExist(userName, password);
        if(isUser) System.out.println("登录成功");
        else System.out.println("用户或密码不对，登录失败");
    }
}
