package name.syndarin.reddittop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syndarin on 9/25/17.
 */

public class RedditTopResponseItem {

    @SerializedName("data")
    RedditItem item;

    public RedditItem getItem() {
        return item;
    }
}
