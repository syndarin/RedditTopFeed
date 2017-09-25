package name.syndarin.reddittop.ui.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import name.syndarin.reddittop.R;
import name.syndarin.reddittop.ui.FragmentLogin;
import name.syndarin.reddittop.ui.FragmentTopThreads;

/**
 * Created by syndarin on 9/24/17.
 */

public class Navigator {

    private FragmentManager fragmentManager;

    public Navigator(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showLogin() {
        replaceFragment(new FragmentLogin());
    }

    public void showRedditTop50() {
        replaceFragment(new FragmentTopThreads());
    }

    public void showThreadPicture(String url) {
        // TODO
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
}
