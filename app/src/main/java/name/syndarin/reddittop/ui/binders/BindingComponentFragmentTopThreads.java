package name.syndarin.reddittop.ui.binders;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.Subject;
import name.syndarin.reddittop.R;
import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.ui.adapters.RedditItemsAdapter;

/**
 * Created by syndarin on 25.10.17.
 */

public class BindingComponentFragmentTopThreads implements DataBindingComponent {

    @BindingAdapter({"items", "clickSubject"})
    public void bindAdapter(RecyclerView view, List<RedditItem> items, Subject<RedditItem> clickSubject) {
        RedditItemsAdapter adapter = (RedditItemsAdapter) view.getAdapter();
        if (adapter == null) {
            adapter = new RedditItemsAdapter(clickSubject);
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        }

        adapter.updateItems(items);
    }

    @BindingAdapter("url")
    public void loadImage(ImageView view, String url) {
        if (url != null) {
            Picasso.with(view.getContext()).load(url).into(view);
        }
    }

    @Override
    public BindingComponentItemRedditTop getBindingComponentItemRedditTop() {
        return null;
    }

    @Override
    public BindingComponentFragmentTopThreads getBindingComponentFragmentTopThreads() {
        return this;
    }
}
