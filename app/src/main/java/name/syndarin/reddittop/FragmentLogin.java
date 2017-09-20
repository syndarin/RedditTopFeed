package name.syndarin.reddittop;

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

import name.syndarin.reddittop.databinding.BindingFragmentLogin;

/**
 * Created by syndarin on 9/19/17.
 */

public class FragmentLogin extends Fragment {

    @BindingAdapter({"url", "webViewClient"})
    public static void initializeWebView(WebView webView, String url, WebViewClient webViewClient) {
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(url);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingFragmentLogin binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        ViewModelLogin viewModel = new ViewModelLogin();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
