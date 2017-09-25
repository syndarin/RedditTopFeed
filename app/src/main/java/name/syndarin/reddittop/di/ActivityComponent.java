package name.syndarin.reddittop.di;

import dagger.Subcomponent;
import name.syndarin.reddittop.ui.navigation.ExternalNavigator;
import name.syndarin.reddittop.ui.navigation.Navigator;
import name.syndarin.reddittop.viewmodel.ViewModelLogin;
import name.syndarin.reddittop.viewmodel.ViewModelTopThreads;

/**
 * Created by syndarin on 9/24/17.
 */

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    Navigator getNavigator();

    ExternalNavigator getExternalNavigator();

    void inject(ViewModelLogin viewModel);

    void inject(ViewModelTopThreads viewModel);
}
