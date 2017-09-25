package name.syndarin.reddittop.di;

import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import name.syndarin.reddittop.ui.navigation.ExternalNavigator;
import name.syndarin.reddittop.ui.navigation.Navigator;

/**
 * Created by syndarin on 9/24/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Navigator getNavigator() {
        return new Navigator(activity.getSupportFragmentManager());
    }

    @Provides
    @ActivityScope
    ExternalNavigator getExternalNavigator() {
        return new ExternalNavigator(activity);
    }

}
