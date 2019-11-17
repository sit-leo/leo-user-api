package app.leo.user.DTO;

public class AdminUserCreateRequest {

    private UserDTO userDTO;
    private AdminProfileDTO adminProfileDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public AdminProfileDTO getAdminProfileDTO() {
        return adminProfileDTO;
    }

    public void setAdminProfileDTO(AdminProfileDTO adminProfileDTO) {
        this.adminProfileDTO = adminProfileDTO;
    }
}
