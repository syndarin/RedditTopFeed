package name.syndarin.reddittop;

import android.databinding.ObservableField;
import android.util.Log;
import android.webkit.WebViewClient;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by syndarin on 9/20/17.
 */

public class ViewModelLogin {

    public final ObservableField<String> oauthUrl = new ObservableField<>();
    public final ObservableField<WebViewClient> webViewClient = new ObservableField<>();

    public ViewModelLogin() {
        OAuthUrlBuilder builder = new OAuthUrlBuilder();
        oauthUrl.set(builder.buildOAuthUrl("RANDOM_STRING"));
        RedditOAuthWebViewClient client = new RedditOAuthWebViewClient(OAuthUrlBuilder.REDIRECT_URI, "RANDOM_STRING");
        webViewClient.set(client);

        RedditHttpClient httpClient = new RedditHttpClient(new OkHttpClient());

        client.getTokenObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(code -> httpClient.obtainAccessToken(OAuthUrlBuilder.CLIENT_ID, code, OAuthUrlBuilder.REDIRECT_URI))

                .subscribe(oauthResponse -> {
                            Log.i("ZZZ", "OAuthCode " + oauthResponse);
                        }
                , Throwable::printStackTrace);
    }
}
