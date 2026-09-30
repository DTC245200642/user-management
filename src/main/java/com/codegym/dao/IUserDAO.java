package com.codegym.dao;

import com.codegym.model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void insertUser(User user) throws SQLException;
    User selectUser(int id);
    List<User> selectAllUsers();
    boolean deleteUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
    
    // Các phương thức dùng Stored Procedure
    User getUserById(int id);
    void insertUserStore(User user) throws SQLException;

    // Phương thức xử lý Transaction
    void addUserTransaction(User user, int[] permissionIds) throws SQLException;
}
public interface IUserDAO {
    // Các phương thức cũ...
    void insertUpdateUseTransaction() throws SQLException;
}
public interface IUserDAO {
   
    List<User> selectAllUsersStore() throws SQLException;
    void updateUserStore(User user) throws SQLException;
    void deleteUserStore(int id) throws SQLException;
}