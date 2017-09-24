package name.syndarin.reddittop.ui;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import name.syndarin.reddittop.R;
import name.syndarin.reddittop.databinding.BindingFragmentLogin;
import name.syndarin.reddittop.di.ActivityComponent;
import name.syndarin.reddittop.viewmodel.ViewModelLogin;

/**
 * Created by syndarin on 9/19/17.
 */

public class FragmentLogin extends Fragment {

    @BindingAdapter({"url", "webViewClient"})
    public static void initializeWebView(WebView webView, String url, WebViewClient webViewClient) {
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(url);
    }

    private ViewModelLogin viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingFragmentLogin binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        viewModel = new ViewModelLogin();
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
