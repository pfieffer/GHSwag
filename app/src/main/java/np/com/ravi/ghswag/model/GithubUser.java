package np.com.ravi.ghswag.model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Date;

/**
 * Created by ravi on 12/27/17.
 */

public class GithubUser {
    //POJO Class

    @SerializedName("login")
    String login; //GH Username

    @SerializedName("avatar_url")
    URL avatarUrl; //URL of the display picture

    @SerializedName("name")
    String name; //Name as set by user in Github

    @SerializedName("location")
    String location;

    @SerializedName("bio")
    String bio;

    @SerializedName("public_repos")
    Integer public_repos; //number of public repos

    @SerializedName("public_gists")
    Integer public_gists;

    @SerializedName("followers")
    Integer followers; //number of followers

    @SerializedName("following")
    Integer following;

    @SerializedName("created_at")
    Date created_at; //user join date

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public URL getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(URL avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(Integer public_repos) {
        this.public_repos = public_repos;
    }

    public Integer getPublic_gists() {
        return public_gists;
    }

    public void setPublic_gists(Integer public_gists) {
        this.public_gists = public_gists;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
