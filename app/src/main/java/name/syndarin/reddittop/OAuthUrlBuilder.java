package name.syndarin.reddittop;

import okhttp3.HttpUrl;

/**
 * Created by syndarin on 9/20/17.
 */

public class OAuthUrlBuilder {

    public static final String CLIENT_ID = "9wQAF4yTB6o2hA";
    private static final String RESPONSE_TYPE = "code";
    public static final String REDIRECT_URI = "http://127.0.0.1/callback";
    private static final String SCOPE = "read";

    public String buildOAuthUrl(String randomStateString) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("www.reddit.com")
                .addPathSegment("api")
                .addPathSegments("v1")
                .addPathSegment("authorize")
                .addQueryParameter("client_id", CLIENT_ID)
                .addQueryParameter("response_type", RESPONSE_TYPE)
                .addQueryParameter("state", randomStateString)
                .addEncodedQueryParameter("redirect_uri", REDIRECT_URI)
                .addQueryParameter("duration", "permanent")
                .addQueryParameter("scope", SCOPE)
                .build()
                .toString();
    }

}
