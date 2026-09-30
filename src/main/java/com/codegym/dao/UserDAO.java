@Override
public void addUserTransaction(User user, int[] permissionIds) throws SQLException {
    Connection connection = null;
    PreparedStatement pstmtUser = null;
    PreparedStatement pstmtAssignment = null;
    ResultSet rs = null;
    
    try {
        connection = getConnection();
        
        // 1. Tắt auto-commit để bắt đầu Transaction
        connection.setAutoCommit(false);
        
        // 2. Chèn dữ liệu vào bảng users và lấy lại ID vừa tạo
        String insertUserSql = "INSERT INTO users (name, email, country) VALUES (?, ?, ?)";
        pstmtUser = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
        pstmtUser.setString(1, user.getName());
        pstmtUser.setString(2, user.getEmail());
        pstmtUser.setString(3, user.getCountry());
        pstmtUser.executeUpdate();
        
        // 3. Lấy ID của user vừa được chèn
        rs = pstmtUser.getGeneratedKeys();
        int userId = 0;
        if (rs.next()) {
            userId = rs.getInt(1);
        }
        
        // 4. Chèn dữ liệu vào bảng user_permission
        if (permissionIds != null && permissionIds.length > 0) {
            String insertPermissionSql = "INSERT INTO user_permission (user_id, permission_id) VALUES (?, ?)";
            pstmtAssignment = connection.prepareStatement(insertPermissionSql);
            
            for (int permissionId : permissionIds) {
                pstmtAssignment.setInt(1, userId);
                pstmtAssignment.setInt(2, permissionId);
                pstmtAssignment.executeUpdate();
            }
        }
        
        // 5. Nếu mọi thứ thành công, tiến hành Commit
        connection.commit();
        System.out.println("Transaction đã được commit thành công!");
        
    } catch (SQLException e) {
        // 6. Nếu có lỗi xảy ra, Rollback lại toàn bộ dữ liệu
        try {
            if (connection != null) {
                connection.rollback();
                System.out.println("Có lỗi xảy ra! Transaction đã bị rollback.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    } finally {
        // 7. Dọn dẹp tài nguyên và bật lại auto-commit
        if (rs != null) rs.close();
        if (pstmtUser != null) pstmtUser.close();
        if (pstmtAssignment != null) pstmtAssignment.close();
        if (connection != null) {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
String insertUserSql = "INSERT INTO users_wrong (name, email, country) VALUES (?, ?, ?)";