package np.com.ravi.ghswag.model;

import com.google.gson.annotations.SerializedName;

public class UserRepo {
    @SerializedName("has_issues")
    private Boolean has_issues;

    @SerializedName("has_pages")
    private Boolean has_pages;

    private String description;

    private String pushed_at;

    private String updated_at;

    @SerializedName("language")
    private String language;

    @SerializedName("forks_count")
    private int forks_count;

    private String contents_url;

    @SerializedName("watchers_count")
    private int watchers_count;

    @SerializedName("private")
    private Boolean isRepoPrivate;

    private String default_branch;

    @SerializedName("open_issues")
    private String open_issues;

    @SerializedName("id")
    private double id;

    @SerializedName("archived")
    private Boolean archived;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("watchers")
    private String watchers;

    @SerializedName("open_issues_count")
    private int open_issues_count;

    @SerializedName("full_name")
    private String full_name;

    public Boolean getHas_issues() {
        return has_issues;
    }

    public void setHas_issues(Boolean has_issues) {
        this.has_issues = has_issues;
    }

    public Boolean getHas_pages() {
        return has_pages;
    }

    public void setHas_pages(Boolean has_pages) {
        this.has_pages = has_pages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPushed_at() {
        return pushed_at;
    }

    public void setPushed_at(String pushed_at) {
        this.pushed_at = pushed_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public String getContents_url() {
        return contents_url;
    }

    public void setContents_url(String contents_url) {
        this.contents_url = contents_url;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }

    public Boolean getRepoPrivate() {
        return isRepoPrivate;
    }

    public void setRepoPrivate(Boolean repoPrivate) {
        isRepoPrivate = repoPrivate;
    }

    public String getDefault_branch() {
        return default_branch;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }

    public String getOpen_issues() {
        return open_issues;
    }

    public void setOpen_issues(String open_issues) {
        this.open_issues = open_issues;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getWatchers() {
        return watchers;
    }

    public void setWatchers(String watchers) {
        this.watchers = watchers;
    }

    public int getOpen_issues_count() {
        return open_issues_count;
    }

    public void setOpen_issues_count(int open_issues_count) {
        this.open_issues_count = open_issues_count;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @Override
    public String toString() {
        return "UserRepo{" +
                "has_issues=" + has_issues +
                ", has_pages=" + has_pages +
                ", description='" + description + '\'' +
                ", pushed_at='" + pushed_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", language='" + language + '\'' +
                ", forks_count=" + forks_count +
                ", contents_url='" + contents_url + '\'' +
                ", watchers_count=" + watchers_count +
                ", isRepoPrivate=" + isRepoPrivate +
                ", default_branch='" + default_branch + '\'' +
                ", open_issues='" + open_issues + '\'' +
                ", id=" + id +
                ", archived=" + archived +
                ", name='" + name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", watchers='" + watchers + '\'' +
                ", open_issues_count=" + open_issues_count +
                ", full_name='" + full_name + '\'' +
                '}';
    }
}
