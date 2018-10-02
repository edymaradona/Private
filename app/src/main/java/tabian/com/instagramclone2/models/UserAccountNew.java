package tabian.com.instagramclone2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccountNew implements Parcelable {

    private String description;
    private String display_name;
    private long followers;
    private long following;
    private long posts;
    private long waiting;
    private long process;
    private long finish;
    private String profile_photo;
    private String username;
    private String website;
    private String user_id;

    public UserAccountNew(String description, String display_name, long followers, long following, long posts, long waiting, long process, long finish, String profile_photo, String username, String website, String user_id) {
        this.description = description;
        this.display_name = display_name;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        this.waiting = waiting;
        this.process = process;
        this.finish = finish;
        this.profile_photo = profile_photo;
        this.username = username;
        this.website = website;
        this.user_id = user_id;
    }

    public UserAccountNew() {
    }

    protected UserAccountNew(Parcel in) {
        description = in.readString();
        display_name = in.readString();
        followers = in.readLong();
        following = in.readLong();
        posts = in.readLong();
        waiting = in.readLong();
        process = in.readLong();
        finish = in.readLong();
        profile_photo = in.readString();
        username = in.readString();
        website = in.readString();
        user_id = in.readString();
    }

    public static final Creator<UserAccountNew> CREATOR = new Creator<UserAccountNew>() {
        @Override
        public UserAccountNew createFromParcel(Parcel in) {
            return new UserAccountNew(in);
        }

        @Override
        public UserAccountNew[] newArray(int size) {
            return new UserAccountNew[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }

    public long getWaiting() {
        return waiting;
    }

    public void setWaiting(long waiting) {
        this.waiting = waiting;
    }

    public long getProcess() {
        return process;
    }

    public void setProcess(long process) {
        this.process = process;
    }

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserAccountNew{" +
                "description='" + description + '\'' +
                ", display_name='" + display_name + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", posts=" + posts +
                ", waiting=" + waiting +
                ", process=" + process +
                ", finish=" + finish +
                ", profile_photo='" + profile_photo + '\'' +
                ", username='" + username + '\'' +
                ", website='" + website + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(display_name);
        dest.writeLong(followers);
        dest.writeLong(following);
        dest.writeLong(posts);
        dest.writeLong(waiting);
        dest.writeLong(process);
        dest.writeLong(finish);
        dest.writeString(profile_photo);
        dest.writeString(username);
        dest.writeString(website);
        dest.writeString(user_id);
    }
}
