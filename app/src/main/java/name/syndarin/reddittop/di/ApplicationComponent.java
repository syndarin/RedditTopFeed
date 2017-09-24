package name.syndarin.reddittop.di;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import name.syndarin.reddittop.reddit.RedditOAuthAuthenticator;
import name.syndarin.reddittop.ui.MainActivity;

/**
 * Created by syndarin on 9/24/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Gson getGson();

    RedditOAuthAuthenticator getRedditOAuthAuthenticator();

    void inject(MainActivity activity);

    ActivityComponent plus(ActivityModule activityModule);
}
