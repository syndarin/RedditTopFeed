package name.syndarin.reddittop.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import io.reactivex.subjects.Subject;
import name.syndarin.reddittop.entity.RedditItem;

/**
 * Created by syndarin on 9/25/17.
 */

public class ViewModelRedditItem {

    public final ObservableField<RedditItem> redditItem = new ObservableField<>();

    private Subject<RedditItem> clickSubject;

    public ViewModelRedditItem(Subject<RedditItem> clickSubject) {
        this.clickSubject = clickSubject;
    }

    public void setRedditItem(RedditItem redditItem) {
        this.redditItem.set(redditItem);
    }

    public void onThumbnailClicked(View view) {
        clickSubject.onNext(redditItem.get());
    }
}
