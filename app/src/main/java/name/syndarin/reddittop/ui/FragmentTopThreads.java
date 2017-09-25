package name.syndarin.reddittop.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.Subject;
import name.syndarin.reddittop.R;
import name.syndarin.reddittop.databinding.BindingFragmentRedditTop;
import name.syndarin.reddittop.di.ActivityComponent;
import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.ui.adapters.RedditItemsAdapter;
import name.syndarin.reddittop.viewmodel.ViewModelTopThreads;

/**
 * Created by syndarin on 9/25/17.
 */

public class FragmentTopThreads extends Fragment implements View.OnScrollChangeListener {

    @BindingAdapter({"items", "clickSubject"})
    public static void bindAdapter(RecyclerView view, List<RedditItem> items, Subject<RedditItem> clickSubject) {
        RedditItemsAdapter adapter = (RedditItemsAdapter) view.getAdapter();
        if (adapter == null) {
            adapter = new RedditItemsAdapter(clickSubject);
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        }

        adapter.updateItems(items);
    }

    @BindingAdapter("url")
    public static void loadImage(ImageView view, String url) {
        if (url != null) {
            Picasso.with(view.getContext()).load(url).into(view);
        }
    }

    @BindingAdapter("comments")
    public static void formatCommentsNumber(TextView view, int numComments) {
        view.setText(view.getResources().getString(R.string.template_num_comments, numComments));
    }

    @BindingAdapter("created")
    public static void formatCreatedAt(TextView view, long createTimestampUtc) {
        long createdAtMillis = TimeUnit.SECONDS.toMillis(createTimestampUtc);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAtMillis);
        String createdDate = SimpleDateFormat.getDateTimeInstance().format(calendar.getTime());

        long millisSinceCreation = System.currentTimeMillis() - createdAtMillis;

        long hours = TimeUnit.MILLISECONDS.toHours(millisSinceCreation);
        long minutesChunk = millisSinceCreation - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(minutesChunk);

        Resources resources = view.getResources();

        String createdString = resources.getString(R.string.template_time_interval, hours, minutes);

        view.setText(view.getResources().getString(R.string.template_created, createdDate, createdString));
    }

    @BindingAdapter("scrollListener")
    public static void setScrollListener(RecyclerView view, View.OnScrollChangeListener listener) {
        view.setOnScrollChangeListener(listener);
    }

    public final ObservableField<View.OnScrollChangeListener> onScrollChangeListener = new ObservableField<>(this);

    private ViewModelTopThreads viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingFragmentRedditTop binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reddit_top, container, false);
        viewModel = new ViewModelTopThreads();
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null && activity instanceof MainActivity) {
            ActivityComponent component = ((MainActivity) activity).getComponent();
            component.inject(viewModel);
        } else {
            throw new RuntimeException("Fail fast: activity is null or has an incompatible type");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResumeView();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPauseView();
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        RecyclerView recyclerView = (RecyclerView) view;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItems = layoutManager.getItemCount();
        int visibleItems = layoutManager.getChildCount();
        int firstVisible = layoutManager.findFirstVisibleItemPosition();

        if ((firstVisible + visibleItems) >= totalItems) {
            viewModel.loadMore();
        }
    }
}
