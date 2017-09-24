package name.syndarin.reddittop.reddit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by syndarin on 9/24/17.
 */

public class TokenStorage implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_EXPIRATION_TIME = "expiration_time_ms";

    private String oAuthToken;
    private String refreshToken;
    private long expirationTime;

    private SharedPreferences preferences;

    public TokenStorage(Context context) {
        preferences = context.getSharedPreferences("oauth_prefs", Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
        reloadData();
    }

    public String getOAuthToken() {
        return oAuthToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    boolean isTokenValid() {
        return !TextUtils.isEmpty(oAuthToken) && expirationTime > System.currentTimeMillis();
    }

    void update(AuthenticationTokenResponse tokenResponse) {
        preferences.edit()
                .putString(KEY_ACCESS_TOKEN, tokenResponse.getAccessToken())
                .putString(KEY_REFRESH_TOKEN, tokenResponse.getRefreshToken())
                .putLong(KEY_EXPIRATION_TIME, System.currentTimeMillis()
                        + TimeUnit.SECONDS.toMillis(tokenResponse.getExpiresIn()))
                .apply();
    }

    private void reloadData() {
        oAuthToken = preferences.getString(KEY_ACCESS_TOKEN, null);
        refreshToken = preferences.getString(KEY_REFRESH_TOKEN, null);
        expirationTime = preferences.getLong(KEY_EXPIRATION_TIME, 0);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        reloadData();
    }
}
