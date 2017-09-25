package name.syndarin.reddittop.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.repository.RedditTopRepository;
import name.syndarin.reddittop.ui.navigation.ExternalNavigator;

/**
 * Created by syndarin on 9/25/17.
 */

public class ViewModelTopThreads {

    private static final String TAG = ViewModelTopThreads.class.getSimpleName();

    public final ObservableList<RedditItem> redditItems = new ObservableArrayList<>();

    public final ObservableField<PublishSubject<RedditItem>> thumbnailClickSubject =
            new ObservableField<>(PublishSubject.create());

    @Inject
    RedditTopRepository redditTopRepository;

    @Inject
    ExternalNavigator externalNavigator;

    private CompositeDisposable subscriptions;

    private Disposable subscriptionLoadMore;

    public void onResumeView() {
        subscriptions = new CompositeDisposable();

        subscriptions.add(thumbnailClickSubject.get()
                .subscribe(redditItem -> {
                    String url = redditItem.getFullSizePreview();
                    if (!TextUtils.isEmpty(url)) {
                        externalNavigator.openExternalLink(url);
                    }
                }));

        subscriptions.add(redditTopRepository.reloadItems()
                .subscribeOn(Schedulers.io())
                .subscribe(this.redditItems::addAll, throwable -> Log.e(TAG, "Can't reload items", throwable)));
    }

    public void onPauseView() {
        subscriptions.dispose();
        subscriptions = null;
    }

    public void loadMore() {
        Log.i("ZZZ", "load more items");
        if (subscriptionLoadMore == null) {
            subscriptionLoadMore = redditTopRepository.loadNextChunk()
                    .subscribeOn(Schedulers.io())
                    .doFinally(() -> {
                        subscriptionLoadMore.dispose();
                        subscriptionLoadMore = null;
                    })
                .subscribe(this.redditItems::addAll, throwable -> Log.e(TAG, "Can't reload items", throwable));
        }

    }

}
