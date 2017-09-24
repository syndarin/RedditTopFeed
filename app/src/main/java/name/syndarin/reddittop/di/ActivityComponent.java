package name.syndarin.reddittop.di;

import dagger.Subcomponent;
import name.syndarin.reddittop.ui.navigation.Navigator;
import name.syndarin.reddittop.viewmodel.ViewModelLogin;

/**
 * Created by syndarin on 9/24/17.
 */

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    Navigator getNavigator();

    void inject(ViewModelLogin viewModel);
}
