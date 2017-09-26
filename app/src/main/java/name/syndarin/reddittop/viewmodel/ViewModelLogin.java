package name.syndarin.reddittop.viewmodel;

import android.databinding.ObservableField;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.reddittop.reddit.OAuthException;
import name.syndarin.reddittop.reddit.RedditOAuthAuthenticator;
import name.syndarin.reddittop.reddit.RedditOAuthWebViewClient;
import name.syndarin.reddittop.ui.navigation.ExternalNavigator;
import name.syndarin.reddittop.ui.navigation.Navigator;

/**
 * Created by syndarin on 9/20/17.
 */

public class ViewModelLogin {

    public final ObservableField<String> oauthUrl = new ObservableField<>();
    public final ObservableField<WebViewClient> webViewClient = new ObservableField<>();

    @Inject
    RedditOAuthAuthenticator authenticator;

    @Inject
    RedditOAuthWebViewClient redditWebViewClient;

    @Inject
    Navigator navigator;

    @Inject
    ExternalNavigator externalNavigator;

    private CompositeDisposable subscriptions = new CompositeDisposable();

    public void onResumeView() {

        oauthUrl.set(authenticator.buildOAuthUrl());
        webViewClient.set(redditWebViewClient);

        subscriptions.add(
                authenticator.getAuthenticationStatusObservable()
                        .subscribeOn(Schedulers.io())
                        .doOnError(throwable -> {
                            if (throwable instanceof OAuthException) {
                                externalNavigator.finish();
                            }
                        })
                        .subscribe(result -> {
                            if (result) {
                                navigator.showRedditTop50();
                            }
                        }));
    }

    public void onPauseView() {
        subscriptions.dispose();
    }
}
