package com.testerhome.nativeandroid.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.oauth2.AuthenticationService;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

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

        FrameLayout layout = (FrameLayout)findViewById(R.id.container);
        layout.addView(mWebView = new WebView(this));

        mWebView.getSettings().getJavaScriptEnabled();
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(AuthenticationService.REDIRECT_URI)) {
                    try {
                        if (url.contains("code=")) {
                            String code = url.substring(url.indexOf("=") + 1, url.indexOf("&"));

                            //Generate URL for requesting Access Token
                            String accessTokenUrl = AuthenticationService.getAccessTokenUrl(auth_code = code);
                            //We make the request in a AsyncTask
                            new PostRequestAsyncTask().execute(accessTokenUrl);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

        });

        mWebView.loadUrl(AuthenticationService.getAuthorizationUrl());

    }

    private class PostRequestAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(WebViewActivity.this, "", "Loading...", true);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            if(urls.length>0){

                MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");

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
                    if (response.isSuccessful()){
                        //Convert the string result to a JSON Object
                        Log.e("Tokenm", "response body:" + response.body().string());
                        JSONObject resultJson = new JSONObject(response.body().string());
                        //Extract data from JSON Response
                        int expiresIn = resultJson.has("expires_in") ? resultJson.getInt("expires_in") : 0;

                        String accessToken = resultJson.has("access_token") ? resultJson.getString("access_token") : null;
                        Log.e("Tokenm", "access token:" + accessToken);
                        if(expiresIn>0 && accessToken!=null){
                            Log.e("Authorize", "This is the access Token: " + accessToken + ". It will expires in " + expiresIn + " secs");

                            //Calculate date of expiration
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.SECOND, expiresIn);
                            long expireDate = calendar.getTimeInMillis();

                            ////Store both expires in and access token in shared preferences
                            SharedPreferences preferences = getSharedPreferences("user_info", 0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putLong("expires", expireDate);
                            editor.putString("accessToken", accessToken);
                            editor.commit();

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
        protected void onPostExecute(Boolean status){
            if(pd!=null && pd.isShowing()){
                pd.dismiss();
            }
            if(status){
                //If everything went Ok, change to another activity.
                Intent startProfileActivity = new Intent(WebViewActivity.this, MainActivity.class);
                startActivity(startProfileActivity);
            }
        }

    };
}
