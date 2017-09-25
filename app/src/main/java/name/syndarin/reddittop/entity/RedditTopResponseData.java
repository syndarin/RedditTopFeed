package name.syndarin.reddittop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by syndarin on 9/25/17.
 */

public class RedditTopResponseData {

    @SerializedName("children")
    private List<RedditTopResponseItem> children;

    @SerializedName("after")
    private String after;

    public List<RedditTopResponseItem> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }
}
