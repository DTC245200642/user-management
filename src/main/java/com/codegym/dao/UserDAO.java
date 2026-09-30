@Override
    public void insertUpdateUseTransaction() {
        // Khai báo các câu lệnh SQL mẫu cho Employee
        String SQL_TABLE_DROP = "DROP TABLE IF EXISTS Employee;";
        String SQL_TABLE_CREATE = "CREATE TABLE Employee (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), salary DECIMAL(10,2), created_at TIMESTAMP);";
        String SQL_INSERT = "INSERT INTO Employee (name, salary, created_at) VALUES (?, ?, ?);";
        String SQL_UPDATE = "UPDATE Employee SET salary = ? WHERE name = ?;";

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             PreparedStatement psInsert = conn.prepareStatement(SQL_INSERT);
             PreparedStatement psUpdate = conn.prepareStatement(SQL_UPDATE)) {

            statement.execute(SQL_TABLE_DROP);
            statement.execute(SQL_TABLE_CREATE);

            // 1. Tắt chế độ lưu tự động để bắt đầu Transaction
            conn.setAutoCommit(false); 

            // 2. Chạy danh sách lệnh Insert
            psInsert.setString(1, "Quynh");
            psInsert.setBigDecimal(2, new BigDecimal(10));
            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            psInsert.setString(1, "Ngan");
            psInsert.setBigDecimal(2, new BigDecimal(20));
            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            // 3. Chạy lệnh Update (Cố tình tạo lỗi index để test rollback)
            psUpdate.setBigDecimal(2, new BigDecimal(999.99)); // Cố tình dùng sai index 2 thay vì 1
            psUpdate.setString(2, "Quynh");
            psUpdate.execute();

            // 4. Commit nếu không có lỗi
            conn.commit();
            conn.setAutoCommit(true);
} catch (Exception e) {
        System.out.println("Lỗi xảy ra, Transaction sẽ tự động huỷ bỏ (rollback)!");
        try {
            // Thêm lệnh gọi rollback thủ công nếu conn chưa bị đóng
            // (hoặc nếu dùng try-with-resources, connection sẽ rollback khi đóng nếu chưa commit)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    }