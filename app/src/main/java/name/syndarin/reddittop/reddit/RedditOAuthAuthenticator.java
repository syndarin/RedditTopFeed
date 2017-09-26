package name.syndarin.reddittop.reddit;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by syndarin on 9/20/17.
 */

public class RedditOAuthAuthenticator {

    private PublishSubject<Boolean> authenticationStatusSubject = PublishSubject.create();

    private OkHttpClient okHttpClient;

    private RedditOAuthConfig config;

    private String randomStateString;

    private RedditOAuthWebViewClient webViewClient;

    private TokenStorage tokenStorage;

    private Gson gson;

    public RedditOAuthAuthenticator(RedditOAuthConfig config,
                                    String randomStateString,
                                    OkHttpClient okHttpClient,
                                    RedditOAuthWebViewClient oAuthWebViewClient,
                                    TokenStorage tokenStorage,
                                    Gson gson) {
        this.config = config;
        this.randomStateString = randomStateString;
        this.okHttpClient = okHttpClient;
        this.webViewClient = oAuthWebViewClient;
        this.tokenStorage = tokenStorage;
        this.gson = gson;

        webViewClient.getRequestTokenObservable()
                .observeOn(Schedulers.io())
                .flatMap(requestToken -> obtainAccessToken(requestToken, config.REDIRECT_URI))
                .map(authResponse -> gson.fromJson(authResponse, AuthenticationTokenResponse.class))
                .subscribe(response -> {
                    tokenStorage.update(response);
                    authenticationStatusSubject.onNext(true);
                }, authenticationStatusSubject::onError);
    }

    public String buildOAuthUrl() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("www.reddit.com")
                .addPathSegment("api")
                .addPathSegments("v1")
                .addPathSegment("authorize")
                .addQueryParameter("client_id", config.CLIENT_ID)
                .addQueryParameter("response_type", config.RESPONSE_TYPE)
                .addQueryParameter("state", randomStateString)
                .addEncodedQueryParameter("redirect_uri", config.REDIRECT_URI)
                .addQueryParameter("duration", "permanent")
                .addQueryParameter("scope", config.SCOPE)
                .build()
                .toString();
    }

    private Single<String> obtainAccessToken(String code, String redirectUrl) {
        SingleSubject<String> resultSubject = SingleSubject.create();

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", code)
                .addEncoded("redirect_uri", redirectUrl)
                .build();

        String base64 = Base64.encodeToString((config.CLIENT_ID + ":" + "").getBytes(), Base64.NO_WRAP);

        Request request = new Request.Builder()
                .url("https://www.reddit.com/api/v1/access_token")
                .header("Authorization", "Basic " + base64)
                .post(body)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            resultSubject.onSuccess(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            resultSubject.onError(e);
        }

        return resultSubject;
    }

    public boolean isAuthenticated() {
        return tokenStorage.isTokenValid();
    }

    public String getAccessToken() {
        return tokenStorage.getOAuthToken();
    }

    public Observable<Boolean> getAuthenticationStatusObservable() {
        return authenticationStatusSubject;
    }
}
