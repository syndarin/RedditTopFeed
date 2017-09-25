package name.syndarin.reddittop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syndarin on 9/25/17.
 */

public class RedditItem {

    @SerializedName("title")
    private String title;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("author")
    private String author;

    @SerializedName("created_utc")
    private long createdAt;

    @SerializedName("num_comments")
    private int numComments;

    @SerializedName("preview")
    private Preview preview;

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public int getNumComments() {
        return numComments;
    }

    public Preview getPreview() {
        return preview;
    }

    public String getFullSizePreview() {
        return preview != null ? preview.getFirstImageUrl() : null;
    }
}
