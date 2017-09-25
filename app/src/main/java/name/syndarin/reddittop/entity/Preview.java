package name.syndarin.reddittop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by syndarin on 9/25/17.
 */

public class Preview {

    @SerializedName("images")
    private List<PreviewImage> images;

    public String getFirstImageUrl() {
        return images != null && !images.isEmpty() ? images.get(0).getSourceImageUrl() : null;
    }

}
