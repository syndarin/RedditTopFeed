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
import name.syndarin.reddittop.ui.binders.BindingComponentFragmentTopThreads;
import name.syndarin.reddittop.viewmodel.ViewModelTopThreads;

/**
 * Created by syndarin on 9/25/17.
 */

public class FragmentTopThreads extends Fragment {

    private ViewModelTopThreads viewModel;
    private BindingComponentFragmentTopThreads bindingComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindingComponent = new BindingComponentFragmentTopThreads(getContext());
        BindingFragmentRedditTop binding = BindingFragmentRedditTop.inflate(inflater, bindingComponent);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bindingComponent.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        bindingComponent.onViewStateRestored(savedInstanceState);
    }


}
