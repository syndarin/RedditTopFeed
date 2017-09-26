package name.syndarin.reddittop.reddit;

import android.text.TextUtils;
import android.util.Log;

import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by syndarin on 9/24/17.
 */

public class RedditClient {

    private static final String URL = "https://oauth.reddit.com/top";

    private RedditOAuthAuthenticator authenticator;

    private OkHttpClient httpClient;

    public RedditClient(RedditOAuthAuthenticator authenticator, OkHttpClient httpClient) {
        this.authenticator = authenticator;
        this.httpClient = httpClient;
    }

    public Single<String> loadTop(String after, int count) {
        return Single.fromCallable(() -> {
            HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                    .scheme("https")
                    .host("oauth.reddit.com")
                    .addPathSegment("top");

            if(!TextUtils.isEmpty(after)) {
                urlBuilder.addQueryParameter("after", after);
            }

            if (count > 0) {
                urlBuilder.addQueryParameter("limit", String.valueOf(count));
            }

            Request request = new Request.Builder()
                    .url(urlBuilder.build())
                    .header("Authorization", "bearer " + authenticator.getAccessToken())
                    .build();

            Response response = httpClient.newCall(request).execute();
            String result = response.body().string();
            return result;
        });
    }

}
