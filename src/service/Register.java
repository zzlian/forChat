package service;

import dao.IsUser;

import java.sql.SQLException;

public class Register {
    /*
     * 用户注册
     */
    public static void register(String userName, String password) throws SQLException, ClassNotFoundException {
        boolean isExit = IsUser.isUser(userName, password);
        if(isExit){
            System.out.println("用户名已经存在");
            return;
        }

    }
}
