package name.syndarin.reddittop.reddit;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
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
        if (uri.toString().startsWith(oAuthConfig.REDIRECT_URI)) {
            processRedirect(uri);
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (url.startsWith(oAuthConfig.REDIRECT_URI)) {
            view.stopLoading();
            processRedirect(Uri.parse(url));
        } else {
            super.onPageStarted(view, url, favicon);
        }
    }

    private void processRedirect(Uri uri) {
        OAuthWebClientResponse response = OAuthWebClientResponse.parseResponse(uri, randomStateString);
        if (response.isSuccessful()) {
            requestTokenSubject.onSuccess(response.getCode());
        } else {
            requestTokenSubject.onError(new OAuthException(response.getError()));
        }
    }

    public Single<String> getRequestTokenObservable() {
        return requestTokenSubject;
    }
}
