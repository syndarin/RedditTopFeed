package name.syndarin.reddittop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syndarin on 9/25/17.
 */

public class SourceImage {

    @SerializedName("url")
    private String url;

    String getUrl() {
        return url;
    }
}
