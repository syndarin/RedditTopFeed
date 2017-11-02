package name.syndarin.reddittop.ui.binders;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import name.syndarin.reddittop.R;

/**
 * Created by syndarin on 25.10.17.
 */

public class BindingComponentItemRedditTop implements DataBindingComponent {

    @BindingAdapter("url")
    public void loadImage(ImageView view, String url) {
        if (url != null) {
            Picasso.with(view.getContext()).load(url).into(view);
        }
    }

    @BindingAdapter("comments")
    public void formatCommentsNumber(TextView view, int numComments) {
        view.setText(view.getResources().getString(R.string.template_num_comments, numComments));
    }

    @BindingAdapter("created")
    public void formatCreatedAt(TextView view, long createTimestampUtc) {
        long createdAtMillis = TimeUnit.SECONDS.toMillis(createTimestampUtc);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAtMillis);
        String createdDate = SimpleDateFormat.getDateTimeInstance().format(calendar.getTime());

        long millisSinceCreation = System.currentTimeMillis() - createdAtMillis;

        long hours = TimeUnit.MILLISECONDS.toHours(millisSinceCreation);
        long minutesChunk = millisSinceCreation - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(minutesChunk);

        Resources resources = view.getResources();

        String createdString = resources.getString(R.string.template_time_interval, hours, minutes);

        view.setText(view.getResources().getString(R.string.template_created, createdDate, createdString));
    }

    @Override
    public BindingComponentItemRedditTop getBindingComponentItemRedditTop() {
        return this;
    }

    @Override
    public BindingComponentFragmentTopThreads getBindingComponentFragmentTopThreads() {
        return null;
    }
}
