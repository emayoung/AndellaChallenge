package andella.challenge.com.andellachallenge.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ememobong on 15/03/2017.
 */
public class GitHubUser {

    @SerializedName("login")
    private String username;
    @SerializedName("avatar_url")
    private String profilePhotoUrl;
    @SerializedName("html_url")
    private String profileUrl;

    GitHubUser(String username, String profilePhotoUrl, String profileUrl ){
        this.username = username;
        this.profilePhotoUrl = profilePhotoUrl;
        this.profileUrl = profileUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
