package app.leo.user.DTO;

public class RecruiterUserCreateRequest {
    private UserDTO user;
    private RecruiterProfileDTO recruiterProfile;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public RecruiterProfileDTO getRecruiterProfile() {
        return recruiterProfile;
    }

    public void setRecruiterProfile(RecruiterProfileDTO recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }
}
