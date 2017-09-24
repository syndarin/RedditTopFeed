package name.syndarin.reddittop.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import name.syndarin.reddittop.R;
import name.syndarin.reddittop.RedditTopApplication;
import name.syndarin.reddittop.di.ActivityComponent;
import name.syndarin.reddittop.di.ActivityModule;
import name.syndarin.reddittop.di.ApplicationComponent;
import name.syndarin.reddittop.reddit.RedditOAuthAuthenticator;
import name.syndarin.reddittop.ui.navigation.Navigator;

public class MainActivity extends AppCompatActivity {

    private ActivityComponent component;

    @Inject
    RedditOAuthAuthenticator authenticator;

    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationComponent component = ((RedditTopApplication) getApplication()).getComponent();
        component.inject(this);

        this.component = component.plus(new ActivityModule(this));
        navigator = this.component.getNavigator();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticator.isAuthenticated()) {
            navigator.showRedditTop50();
        } else {
            navigator.showLogin();
        }
    }

    public ActivityComponent getComponent() {
        return component;
    }
}
