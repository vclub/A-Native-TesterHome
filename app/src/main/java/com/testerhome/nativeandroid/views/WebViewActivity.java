package com.testerhome.nativeandroid.views;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.auth.TesterHomeAccountService;
import com.testerhome.nativeandroid.models.UserResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;
import com.testerhome.nativeandroid.oauth2.AuthenticationService;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import retrofit.RetrofitError;

/**
 * Created by vclub on 15/9/18.
 */
public class WebViewActivity extends BackBaseActivity {

    private WebView mWebView;
    private ProgressDialog pd;

    private String auth_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_base);
        setCustomTitle("登录");

        FrameLayout layout = (FrameLayout) findViewById(R.id.container);
        layout.addView(mWebView = new WebView(this));

        mWebView.getSettings().getJavaScriptEnabled();
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // http://testerhome.com/oauth/authorize/
                if (url.startsWith(AuthenticationService.HTTP_AUTHORIZATION_URL)) {
                    try {
                        auth_code = url.substring(url.lastIndexOf("/") + 1);
                        //Generate URL for requesting Access Token
                        // String accessTokenUrl = AuthenticationService.getAccessTokenUrl(auth_code = code);
                        //We make the request in a AsyncTask
                        new PostRequestAsyncTask().execute(AuthenticationService.ACCESS_TOKEN_URL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(url.equals(AuthenticationService.LOGIN_URL)){
                    url = AuthenticationService.HTTPS_LOGIN_URL;
                }else if(url.equals(AuthenticationService.BASEURL)){
                    url = AuthenticationService.HTTPS_BASEURL;
                }
//                return super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }

        });

        Log.e("test", AuthenticationService.getAuthorizationUrl());
        mWebView.loadUrl(AuthenticationService.getAuthorizationUrl());

    }

    private class PostRequestAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(WebViewActivity.this, "", "Loading...", true);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            if (urls.length > 0) {

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                String url = urls[0];
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody formBody = new FormEncodingBuilder()
                        .add("client_id", AuthenticationService.getAuthorize_client_id())
                        .add("code", auth_code)
                        .add("grant_type", "authorization_code")
                        .add("redirect_uri", AuthenticationService.REDIRECT_URI)
                        .add("client_secret", "3a20127eb087257ad7196098bfd8240746a66b0550d039eb2c1901c025e7cbea")
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //Convert the string result to a JSON Object
                        String responseStr = response.body().string();

                        Log.e("Tokenm", "response body:" + responseStr);
                        JSONObject resultJson = new JSONObject(responseStr);
                        //Extract data from JSON Response
                        int expiresIn = resultJson.has("expires_in") ? resultJson.getInt("expires_in") : 0;

                        String accessToken = resultJson.has("access_token") ? resultJson.getString("access_token") : null;
                        Log.e("Tokenm", "access token:" + accessToken);
                        if (expiresIn > 0 && accessToken != null) {
                            Log.e("Authorize", "This is the access Token: " + accessToken + ". It will expires in " + expiresIn + " secs");

                            //Calculate date of expiration
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.SECOND, expiresIn);
                            long expireDate = calendar.getTimeInMillis();

                            ////Store both expires in and access token in shared preferences
//                            SharedPreferences preferences = getSharedPreferences("user_info", 0);
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putLong("expires", expireDate);
//                            editor.putString("accessToken", accessToken);
//                            editor.commit();

                            getUsername(accessToken);

                            return true;
                        }
                    } else {
                        Log.e("Tokenm", "error:" + response.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            //if (status) {
                //If everything went Ok, change to another activity.
                //Intent startProfileActivity = new Intent(WebViewActivity.this, MainActivity.class);
                //startActivity(startProfileActivity);
            //}
        }

    }

    ;

    private void getUsername(final String token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://testerhome.com/api/v3/hello.json?access_token=" + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject resultJson = new JSONObject(response.body().string());
                    String login = resultJson.optJSONObject("user").optString("login");
                    getUserInfo(login, token);
                } catch (Exception ex) {

                }
            }
        });
    }

    private void getUserInfo(final String login, final String token) {
        TesterHomeApi.getInstance().getTopicsService().getUserInfo(login, token, new retrofit.Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, retrofit.client.Response response) {
                if (userResponse.getUser() != null){
                    TesterHomeAccountService.getInstance(WebViewActivity.this)
                            .signIn(login, token, userResponse.getUser());

                    WebViewActivity.this.finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


}
