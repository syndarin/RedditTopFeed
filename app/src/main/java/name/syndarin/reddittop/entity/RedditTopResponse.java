package name.syndarin.reddittop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syndarin on 9/25/17.
 */

public class RedditTopResponse {

    @SerializedName("data")
    RedditTopResponseData data;

    public RedditTopResponseData getData() {
        return data;
    }
}
