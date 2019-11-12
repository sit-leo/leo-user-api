package app.leo.user.DTO;

import javax.validation.constraints.NotNull;

public class ApplicantUserCreateRequest {

    private UserDTO user;
    private ApplicantProfileDTO applicantProfile;

    @NotNull
    private String recaptcha;

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

    public String getRecaptcha() {
        return recaptcha;
    }

    public void setRecaptcha(String recaptcha) {
        this.recaptcha = recaptcha;
    }
}
