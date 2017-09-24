package name.syndarin.reddittop.reddit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syndarin on 9/24/17.
 */

public class AuthenticationTokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private int expiresIn;

    String getAccessToken() {
        return accessToken;
    }

    String getRefreshToken() {
        return refreshToken;
    }

    int getExpiresIn() {
        return expiresIn;
    }
}
