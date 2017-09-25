package name.syndarin.reddittop.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
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

public class FragmentTopThreads extends Fragment {

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
        long millisSinceCreation = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(createTimestampUtc);

        long hours = TimeUnit.MILLISECONDS.toHours(millisSinceCreation);
        long minutesChunk = millisSinceCreation - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(minutesChunk);

        Resources resources = view.getResources();

        String createdString = resources.getString(R.string.template_time_interval, hours, minutes);

        view.setText(view.getResources().getString(R.string.template_created, createdString));
    }

    private ViewModelTopThreads viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingFragmentRedditTop binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reddit_top, container, false);
        viewModel = new ViewModelTopThreads();
        binding.setViewModel(viewModel);
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
}
