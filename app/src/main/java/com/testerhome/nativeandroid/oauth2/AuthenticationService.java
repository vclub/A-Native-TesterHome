package com.testerhome.nativeandroid.oauth2;

import android.util.Log;

/**
 * Created by vclub on 15/9/18.
 */
public class AuthenticationService {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String TOKEN_TYPE = "token_type";
    private static final String EXPIRES_IN = "expires_in";
    private static final String SCOPE = "scope";
    private static final String EXPIRES_IN_LONG = "expires_in_long";


    public static final String RESPONSE_TYPE_TOKEN = "token";
    public static final String RESPONSE_TYPE_CODE = "code";

    //This is the public api key of our application
    private static final String API_KEY = "6dbe4244";
    //This is the private api key of our application
    private static final String SECRET_KEY = "3a20127eb087257ad7196098bfd8240746a66b0550d039eb2c1901c025e7cbea";
    //This is any string we want to use. This will be used for avoid CSRF attacks. You can generate one here: http://strongpasswordgenerator.com/
    private static final String STATE = "E3ZYKC1T6H2yP4z";
    //This is the url that LinkedIn Auth process will redirect to. We can put whatever we want that starts with http:// or https:// .
    //We use a made up url that we will intercept when redirecting. Avoid Uppercases.
    public static final String REDIRECT_URI = "https://www.imrainbow.com/oauth/callback";
    /*********************************************/

    //These are constants used for build the urls
    private static final String AUTHORIZATION_URL = "https://testerhome.com/oauth/authorize";
    private static final String ACCESS_TOKEN_URL = "https://testerhome.com/oauth/token";
    private static final String SECRET_KEY_PARAM = "client_secret";
    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String GRANT_TYPE_PARAM = "grant_type";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE_VALUE = "code";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String STATE_PARAM = "state";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";
    /*---------------------------------------*/
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";

    public String getAuthorize_response_type() {
        return "code";
    }

    public static String getAuthorize_client_id() {
        return API_KEY;
    }

    public String getAuthorize_redirect_uri() {
        return REDIRECT_URI;
    }

    /**
     * Method that generates the url for get the authorization token from the Service
     *
     * @return Url
     */
    public static String getAuthorizationUrl() {
        Log.e("oauth", AUTHORIZATION_URL
                + QUESTION_MARK + RESPONSE_TYPE_PARAM + EQUALS + RESPONSE_TYPE_VALUE
                + AMPERSAND + CLIENT_ID_PARAM + EQUALS + API_KEY
                + AMPERSAND + STATE_PARAM + EQUALS + STATE
                + AMPERSAND + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI);
        return AUTHORIZATION_URL
                + QUESTION_MARK + RESPONSE_TYPE_PARAM + EQUALS + RESPONSE_TYPE_VALUE
                + AMPERSAND + CLIENT_ID_PARAM + EQUALS + API_KEY
                + AMPERSAND + STATE_PARAM + EQUALS + STATE
                + AMPERSAND + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI;
    }

    /**
     * Method that generates the url for get the access token from the Service
     *
     * @return Url
     */
    public static String getAccessTokenUrl(String authorizationToken) {
        return ACCESS_TOKEN_URL
                + QUESTION_MARK
                + GRANT_TYPE_PARAM + EQUALS + GRANT_TYPE
                + AMPERSAND
                + RESPONSE_TYPE_VALUE + EQUALS + authorizationToken
                + AMPERSAND
                + CLIENT_ID_PARAM + EQUALS + API_KEY
                + AMPERSAND
                + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI
                + AMPERSAND
                + SECRET_KEY_PARAM + EQUALS + SECRET_KEY;
    }
}
