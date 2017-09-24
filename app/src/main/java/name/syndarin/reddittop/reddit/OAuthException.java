package name.syndarin.reddittop.reddit;

/**
 * Created by syndarin on 9/21/17.
 */

public class OAuthException extends Exception {

    private OAuthWebClientResponse.Error error;

    public OAuthException(OAuthWebClientResponse.Error error) {
        this.error = error;
    }

    public OAuthException(String message) {
        super(message);
    }

    public OAuthWebClientResponse.Error getError() {
        return error;
    }
}
