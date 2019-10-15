package app.leo.user.DTO;

public class ApplicantUserCreateRequest {

    private UserDTO user;
    private ApplicantProfileDTO applicantProfile;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ApplicantProfileDTO getApplicantProfile() {
        return applicantProfile;
    }

    public void setApplicantProfile(ApplicantProfileDTO applicantProfile) {
        this.applicantProfile = applicantProfile;
    }
}
