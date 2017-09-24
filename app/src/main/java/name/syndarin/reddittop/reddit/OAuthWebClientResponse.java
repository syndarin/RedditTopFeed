package name.syndarin.reddittop.reddit;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by syndarin on 9/24/17.
 */

public class OAuthWebClientResponse {

    public enum Error {
        RANDOM_STRING_MISMATCH("request_token_mismatch"),
        ACCESS_DENIED("access_denied"),
        UNSUPPORTED_RESPONSE_TYPE("unsupported_response_type"),
        INVALID_SCOPE("invalid_scope"),
        INVALID_REQUEST("invalid_request"),
        UNKNOWN_ERROR("unknown_error");

        String responseValue;

        Error(String value) {
            this.responseValue = value;
        }

        private static Error byValue(String value) {
            for (Error error : values()) {
                if (error.responseValue.equals(value)) {
                    return error;
                }
            }

            throw new IllegalArgumentException("There is no error with responseValue " + value);
        }
    }

    static OAuthWebClientResponse parseResponse(Uri uri, String randomStateString) {
        String code = null;
        Error error = null;

        String state = uri.getQueryParameter("state");

        if (state.equals(randomStateString)) {
            code = uri.getQueryParameter("code");
            if (TextUtils.isEmpty(code)) {
                String errorValue = uri.getQueryParameter("error");
                error = !TextUtils.isEmpty(errorValue) ? Error.byValue(errorValue) : Error.UNKNOWN_ERROR;
            }

        } else {
            error = Error.RANDOM_STRING_MISMATCH;
        }

        return new OAuthWebClientResponse(code, error);
    }

    private final String code;
    private final Error error;

    private OAuthWebClientResponse(String code, Error error) {
        this.code = code;
        this.error = error;
    }

    public boolean isSuccessful() {
        return !TextUtils.isEmpty(code);
    }

    public String getCode() {
        return code;
    }

    public Error getError() {
        return error;
    }
}
