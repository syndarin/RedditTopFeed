package name.syndarin.reddittop;

import android.databinding.ObservableField;
import android.util.Log;
import android.webkit.WebViewClient;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import okhttp3.HttpUrl;

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

        client.getTokenObservable()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("ZZZ", "Code received " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
