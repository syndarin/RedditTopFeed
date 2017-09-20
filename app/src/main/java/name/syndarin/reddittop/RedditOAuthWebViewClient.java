package name.syndarin.reddittop;

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

    private SingleSubject<String> tokenSubject = SingleSubject.create();

    private String redirectUrl;

    private String randomStateString;

    public RedditOAuthWebViewClient(String redirectUrl, String randomStateString) {
        this.redirectUrl = redirectUrl;
        this.randomStateString = randomStateString;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri uri = request.getUrl();
        Log.i("ZZZ", "Request to " + uri.toString());

        if (uri.toString().startsWith(redirectUrl)) {
            String state = uri.getQueryParameter("state");
            if (state.equals(randomStateString)) {
                tokenSubject.onSuccess(uri.getQueryParameter("code"));
            } else {
                tokenSubject.onError(new IllegalArgumentException("Suspicious response"));
            }

            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    public Single<String> getTokenObservable() {
        return tokenSubject;
    }
}
