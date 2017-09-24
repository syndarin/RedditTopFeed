package name.syndarin.reddittop;

import android.app.Application;

import java.util.UUID;

import name.syndarin.reddittop.di.ApplicationComponent;
import name.syndarin.reddittop.di.ApplicationModule;
import name.syndarin.reddittop.di.DaggerApplicationComponent;

/**
 * Created by syndarin on 9/24/17.
 */

public class RedditTopApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        UUID uuid = UUID.randomUUID();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, uuid.toString()))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
