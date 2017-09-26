package name.syndarin.reddittop.ui.navigation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by syndarin on 9/26/17.
 */

public class ExternalNavigator {

    private Activity activity;

    public ExternalNavigator(Activity activity) {
        this.activity = activity;
    }

    public void openExternalLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }

    public void finish() {
        activity.finish();
    }

}
