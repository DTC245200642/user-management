@Override
    public List<User> selectAllUsersStore() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "{CALL select_all_users()}";
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        }
        return users;
    }

    @Override
    public void updateUserStore(User user) throws SQLException {
        String query = "{CALL update_user(?, ?, ?, ?)}";
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getCountry());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteUserStore(int id) throws SQLException {
        String query = "{CALL delete_user(?)}";
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }