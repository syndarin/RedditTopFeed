package name.syndarin.reddittop.di;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import name.syndarin.reddittop.reddit.RedditOAuthAuthenticator;
import name.syndarin.reddittop.reddit.RedditOAuthConfig;
import name.syndarin.reddittop.reddit.RedditOAuthWebViewClient;
import name.syndarin.reddittop.reddit.TokenStorage;
import okhttp3.OkHttpClient;

/**
 * Created by syndarin on 9/24/17.
 */

@Module
public class ApplicationModule {

    private Application application;
    private final String randomStateString;

    public ApplicationModule(Application application, String randomStateString) {
        this.application = application;
        this.randomStateString = randomStateString;
    }

    @Provides
    @Singleton
    public Gson getGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    public RedditOAuthConfig getOAuthConfig() {
        return new RedditOAuthConfig();
    }

    @Provides
    @Singleton
    public RedditOAuthAuthenticator getOAuthAuthenticator(RedditOAuthConfig oAuthConfig,
                                                          OkHttpClient okHttpClient,
                                                          RedditOAuthWebViewClient webViewClient,
                                                          TokenStorage tokenStorage,
                                                          Gson gson) {
        return new RedditOAuthAuthenticator(oAuthConfig, randomStateString, okHttpClient,
                webViewClient, tokenStorage, gson);
    }

    @Provides
    @Singleton
    public RedditOAuthWebViewClient getRedditOAuthWebViewClient(RedditOAuthConfig oAuthConfig) {
        return new RedditOAuthWebViewClient(oAuthConfig, randomStateString);
    }

    @Provides
    @Singleton
    public TokenStorage getTokenStorage() {
        return new TokenStorage(application);
    }

}
