package name.syndarin.reddittop.ui.binders;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by syndarin on 04.11.17.
 */

public class BasicBindingComponent implements android.databinding.DataBindingComponent {

    @BindingAdapter({"onScrollChangedListener"})
    public void setOnScrollChangedListener(RecyclerView view, View.OnScrollChangeListener listener) {
        view.setOnScrollChangeListener(listener);
    }

    @Override
    public BindingComponentItemRedditTop getBindingComponentItemRedditTop() {
        return null;
    }

    @Override
    public BindingComponentFragmentTopThreads getBindingComponentFragmentTopThreads() {
        return null;
    }

    @Override
    public BasicBindingComponent getBasicBindingComponent() {
        return this;
    }
}
