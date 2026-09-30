private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        
        // Nhận danh sách quyền hạn được check từ form
        String[] permissionsStr = request.getParameterValues("permissions");
        int[] permissions = null;
        
        if (permissionsStr != null) {
            permissions = new int[permissionsStr.length];
            for (int i = 0; i < permissionsStr.length; i++) {
                permissions[i] = Integer.parseInt(permissionsStr[i]);
            }
        }
        
        User newUser = new User(name, email, country);
        
        // Gọi phương thức sử dụng Transaction
        userDAO.addUserTransaction(newUser, permissions);
        
        // Quay lại trang danh sách sau khi lưu thành công
        response.sendRedirect("users");
    }
    case "test-use-tran":
    testUseTran(request, response);
    break;
    private void testUseTran(HttpServletRequest request, HttpServletResponse response) 
        throws SQLException, IOException, ServletException {
    userDAO.insertUpdateUseTransaction();
    System.out.println("Hoàn tất gọi hàm testUseTran!");
    response.sendRedirect("users");
}
List<User> listUser = userDAO.selectAllUsersStore();
request.setAttribute("listUser", listUser);
User book = new User(id, name, email, country);
userDAO.updateUserStore(book);
userDAO.deleteUserStore(id);
