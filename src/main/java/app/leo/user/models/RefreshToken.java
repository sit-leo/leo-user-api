package app.leo.user.models;



public class RefreshToken {

    private String username;
    private String token;
    private String expires;

    public RefreshToken() {
    }

    public RefreshToken(String username, String token, String expires) {
        this.username = username;
        this.token = token;
        this.expires = expires;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
