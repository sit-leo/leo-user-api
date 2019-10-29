package app.leo.user.DTO;

public class OrganizerUserCreateRequest {
    private UserDTO user;
    private OrganizationProfileDTO organizationProfileDTO;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public OrganizationProfileDTO getOrganizationProfileDTO() {
        return organizationProfileDTO;
    }

    public void setOrganizationProfileDTO(OrganizationProfileDTO organizationProfileDTO) {
        this.organizationProfileDTO = organizationProfileDTO;
    }
}
