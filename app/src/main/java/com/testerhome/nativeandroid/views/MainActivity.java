package com.testerhome.nativeandroid.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.nativeandroid.Config;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.auth.TesterHomeAccountService;
import com.testerhome.nativeandroid.fragments.HomeFragment;
import com.testerhome.nativeandroid.fragments.MyFragment;
import com.testerhome.nativeandroid.fragments.TopicsListFragment;
import com.testerhome.nativeandroid.models.TesterUser;
import com.testerhome.nativeandroid.views.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private Fragment homeFragment;
    private Fragment jobFragment;
    private Fragment topicFragment;
    private Fragment myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }

    private void setupView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent,homeFragment).commit();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fm.beginTransaction();
            if(myFragment==null){
                myFragment = new MyFragment();
                fragmentTransaction.add(R.id.realtabcontent,myFragment);
            }
            fragmentTransaction.show(myFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fm.beginTransaction();
        hideAllFragment(fragmentTransaction);




        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            if (homeFragment==null){
                homeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.realtabcontent,homeFragment);
            }
            fragmentTransaction.show(homeFragment);
        } else if (id == R.id.nav_topic) {
            if (topicFragment==null){
                topicFragment = TopicsListFragment.newInstance(Config.TOPICS_TYPE_LAST_ACTIVED);
                fragmentTransaction.add(R.id.realtabcontent,topicFragment);

            }
            fragmentTransaction.show(topicFragment);
        } else if (id == R.id.nav_job) {
            if (jobFragment==null){
                jobFragment = TopicsListFragment.newInstance(Config.TOPIC_JOB_NODEID);
                fragmentTransaction.add(R.id.realtabcontent,jobFragment);

            }
            fragmentTransaction.show(jobFragment);

        } else if (id == R.id.nav_my) {
            if (!(mTesterHomeAccount != null && !TextUtils.isEmpty(mTesterHomeAccount.getLogin()))) {
                startActivityForResult(new Intent(this, WebViewActivity.class), 1);
            }else{
                if(myFragment==null){
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.realtabcontent,myFragment);
                }
                fragmentTransaction.show(myFragment);
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        fragmentTransaction.commit();
        return true;
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {

        if(homeFragment!=null){
            fragmentTransaction.hide(homeFragment);
        }
        if(topicFragment!=null){
            fragmentTransaction.hide(topicFragment);
        }
        if(jobFragment!=null){
            fragmentTransaction.hide(jobFragment);
        }
        if(myFragment!=null){
            fragmentTransaction.hide(myFragment);
        }
    }

    @Nullable
    @Bind(R.id.sdv_account_avatar)
    SimpleDraweeView mAccountAvatar;

    @Nullable
    @Bind(R.id.tv_account_username)
    TextView mAccountUsername;

    @Nullable
    @Bind(R.id.tv_account_email)
    TextView mAccountEmail;

    @OnClick(R.id.sdv_account_avatar)
    void onAvatarClick(){
        if (mTesterHomeAccount != null && !TextUtils.isEmpty(mTesterHomeAccount.getLogin())) {
            startActivity(new Intent(this, UserProfileActivity.class));
        } else {
            startActivity(new Intent(this, WebViewActivity.class));
        }
    }

    TesterUser mTesterHomeAccount;

    private void updateUserInfo() {
        mTesterHomeAccount = TesterHomeAccountService.getInstance(this).getActiveAccountInfo();
        if (!TextUtils.isEmpty(mTesterHomeAccount.getLogin())) {
            mAccountAvatar.setImageURI(Uri.parse(Config.getImageUrl(mTesterHomeAccount.getAvatar_url())));
            mAccountUsername.setText(mTesterHomeAccount.getName());
            mAccountEmail.setText(mTesterHomeAccount.getEmail());
        }
    }
}
