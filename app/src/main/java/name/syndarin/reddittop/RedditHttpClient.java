package name.syndarin.reddittop;

import java.io.IOException;
import android.util.Base64;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.subjects.SingleSubject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by syndarin on 9/21/17.
 */

public class RedditHttpClient {

    OkHttpClient okHttpClient;

    public RedditHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Single<String> obtainAccessToken(String clientId, String code, String redirectUrl) {

        SingleSubject<String> resultSubject = SingleSubject.create();

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", code)
                .addEncoded("redirect_uri", redirectUrl)
                .build();

        String base64 = Base64.encodeToString((clientId + ":" + "").getBytes(), Base64.NO_WRAP);
        Log.i("ZZZ", "Base64 " + base64);

        Request request = new Request.Builder()
                .url("https://www.reddit.com/api/v1/access_token")
                .header("Authorization", "Basic " + base64)
                .post(body)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            Log.i("ZZZ", "Response code " + response.code());
            resultSubject.onSuccess(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            resultSubject.onError(e);
        }

        return resultSubject;
    }

}
