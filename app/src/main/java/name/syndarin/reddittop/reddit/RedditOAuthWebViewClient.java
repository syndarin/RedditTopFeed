package name.syndarin.reddittop.reddit;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.reactivex.Single;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by syndarin on 9/20/17.
 */

public class RedditOAuthWebViewClient extends WebViewClient {

    private SingleSubject<String> requestTokenSubject = SingleSubject.create();

    private RedditOAuthConfig oAuthConfig;

    private String randomStateString;

    public RedditOAuthWebViewClient(RedditOAuthConfig oAuthConfig, String randomStateString) {
        this.oAuthConfig = oAuthConfig;
        this.randomStateString = randomStateString;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        Uri uri = request.getUrl();
        Log.i("ZZZ", "should override url loading " + uri.toString());
        if (uri.toString().startsWith(oAuthConfig.REDIRECT_URI)) {
            OAuthWebClientResponse response = OAuthWebClientResponse.parseResponse(uri, randomStateString);
            if (response.isSuccessful()) {
                requestTokenSubject.onSuccess(response.getCode());
            } else {
                requestTokenSubject.onError(new OAuthException(response.getError()));
            }
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    public Single<String> getRequestTokenObservable() {
        return requestTokenSubject;
    }
}
