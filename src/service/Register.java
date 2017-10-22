package service;

import dao.IsExist;

import java.sql.SQLException;

public class Register {
    /*
     * 用户注册
     */
    public static void register(String userName, String password) throws SQLException, ClassNotFoundException {
        boolean isExit = IsExist.isExist(userName, password);
        if(isExit){
            System.out.println("用户名已经存在");
            return;
        }

    }
}
