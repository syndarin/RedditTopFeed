package name.syndarin.reddittop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syndarin on 9/25/17.
 */

public class PreviewImage {

    @SerializedName("source")
    private SourceImage sourceImage;

    public String getSourceImageUrl() {
        return sourceImage != null ? sourceImage.getUrl() : null;
    }
}
