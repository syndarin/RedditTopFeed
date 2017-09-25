package name.syndarin.reddittop.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.repository.RedditTopRepository;

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

    private CompositeDisposable subscriptions;

    public void onResumeView() {
        subscriptions = new CompositeDisposable();

        subscriptions.add(thumbnailClickSubject.get()
                .subscribe(redditTopResponseItem -> {
                    //TODO open picture url
                }));

        subscriptions.add(redditTopRepository.reloadItems()
                .subscribeOn(Schedulers.io())
                .subscribe(redditItems -> {
                    this.redditItems.addAll(redditItems);
                }, throwable -> Log.e(TAG, "Can't reload items", throwable)));
    }

    public void onPauseView() {
        subscriptions.dispose();
        subscriptions = null;
    }

    public void loadMore() {
        subscriptions.add(redditTopRepository.loadNextChunk()
                .subscribeOn(Schedulers.io())
                .subscribe(redditItems -> {
                    this.redditItems.addAll(redditItems);
                }, throwable -> Log.e(TAG, "Can't reload items", throwable)));
    }

}
