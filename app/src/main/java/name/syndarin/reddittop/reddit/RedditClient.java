package name.syndarin.reddittop.reddit;

import okhttp3.OkHttpClient;

/**
 * Created by syndarin on 9/24/17.
 */

public class RedditClient {

    private TokenStorage tokenStorage;

    private OkHttpClient httpClient;

    public RedditClient(TokenStorage tokenStorage, OkHttpClient httpClient) {
        this.tokenStorage = tokenStorage;
        this.httpClient = httpClient;
    }

    public boolean isReady() {
        return tokenStorage.isTokenValid();
    }

    public void loadTop(int offset, int limit) {
        // TODO
    }

}
