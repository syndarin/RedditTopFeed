package name.syndarin.reddittop.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.syndarin.reddittop.R;
import name.syndarin.reddittop.databinding.BindingFragmentRedditTop;

/**
 * Created by syndarin on 9/25/17.
 */

public class FragmentRedditTop extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingFragmentRedditTop binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reddit_top, container, false);
        return binding.getRoot();
    }
}
