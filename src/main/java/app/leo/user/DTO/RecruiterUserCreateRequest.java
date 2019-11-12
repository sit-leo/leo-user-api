package app.leo.user.DTO;

import javax.validation.constraints.NotNull;

public class RecruiterUserCreateRequest {
    private UserDTO user;
    private RecruiterProfileDTO recruiterProfile;

    @NotNull
    private String recaptcha;

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

    public String getRecaptcha() {
        return recaptcha;
    }

    public void setRecaptcha(String recaptcha) {
        this.recaptcha = recaptcha;
    }
}
