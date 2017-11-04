package name.syndarin.reddittop.ui.binders;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import io.reactivex.subjects.Subject;
import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.ui.adapters.RedditItemsAdapter;

/**
 * Created by syndarin on 25.10.17.
 */

public class BindingComponentFragmentTopThreads extends BasicBindingComponent {

    private RedditItemsAdapter redditItemsAdapter;
    private LinearLayoutManager layoutManager;

    private Parcelable layoutManagerSavedState;

    public BindingComponentFragmentTopThreads(Context context) {
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        redditItemsAdapter = new RedditItemsAdapter();
    }

    @BindingAdapter({"items", "clickSubject"})
    public void bindAdapter(RecyclerView view, List<RedditItem> items, Subject<RedditItem> clickSubject) {
        RedditItemsAdapter adapter = (RedditItemsAdapter) view.getAdapter();
        if (adapter == null) {
            view.setAdapter(redditItemsAdapter);
            view.setLayoutManager(layoutManager);
        }

        redditItemsAdapter.setThumbnailClickSubject(clickSubject);
        redditItemsAdapter.updateItems(items);

        if (layoutManagerSavedState != null && redditItemsAdapter.getItemCount() > 0) {
            layoutManager.onRestoreInstanceState(layoutManagerSavedState);
            layoutManagerSavedState = null;
        }
    }

    @Override
    public BindingComponentFragmentTopThreads getBindingComponentFragmentTopThreads() {
        return this;
    }

    public void onSaveInstanceState(Bundle bundle) {
        Parcelable state = layoutManager.onSaveInstanceState();
        bundle.putParcelable("threads-layout-manager", state);
    }

    public void onViewStateRestored(Bundle bundle) {
        if (bundle != null) {
            Parcelable state = bundle.getParcelable("threads-layout-manager");
            if (state != null && redditItemsAdapter.getItemCount() > 0) {
                layoutManager.onRestoreInstanceState(state);
            } else {
                layoutManagerSavedState = state;
            }
        }
    }
}
