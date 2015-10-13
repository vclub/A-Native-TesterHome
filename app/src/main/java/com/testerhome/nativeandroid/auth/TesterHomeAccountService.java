package com.testerhome.nativeandroid.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TesterUser;
import com.testerhome.nativeandroid.utils.SharedPreferencesHelper;

/**
 * Created by vclub on 15/10/13.
 */
public class TesterHomeAccountService {
    private static TesterHomeAccountService mInstance;
    private final String KEY_DEFAULT_ACCOUNT = "defaultAccount";
    private final String KEY_USER_DATA_USER_ID = "uer_id";
    private final String KEY_USER_DATA_ACCOUNT_NAME = "username";
    private final String KEY_USER_DATA_NICKNAME = "nickname";
    private final String KEY_USER_DATA_AVATAR = "avatar_url";
    private final String KEY_USER_DATA_LOCATION = "location";
    private final String KEY_USER_DATA_WEBSITE = "website";
    private final String KEY_USER_DATA_GITHUB = "github";
    private final String KEY_USER_DATA_EMAIL = "email";
    private final String KEY_USER_DATA_TOKEN = "access_token";

    private Account activeAccount;
    private SharedPreferencesHelper spfHelper;
    private AccountManager mAccountManager;
    private Context mContext;


//    private String access_token;
//    private String token_type;
//    private String expires_in;
//    private String refresh_token;
//    private String craete_at;

    public static synchronized TesterHomeAccountService getInstance(Context ctx){
        if (mInstance == null){
            mInstance = new TesterHomeAccountService(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public TesterHomeAccountService(Context ctx){
        this.mContext = ctx;
        this.spfHelper = new SharedPreferencesHelper(ctx);
        mAccountManager = AccountManager.get(ctx);
        if (hasDefaultAccount()){
            activeAccount = getDefaultAccount();
        }
    }

    private boolean hasDefaultAccount() {
        return getDefaultAccount() != null;
    }

    private Account getDefaultAccount() {
        String accountName = spfHelper.getValue(KEY_DEFAULT_ACCOUNT);

        return findAccountByUsername(accountName);
    }

    private void setDefaultAccount(Account defaultAccount) {
        spfHelper.setValue(KEY_DEFAULT_ACCOUNT, defaultAccount.name);

        boolean foundAccount = false;
        Account[] accounts = mAccountManager.getAccounts();
        for (int i = 0; i < accounts.length && !foundAccount; i++) {
            if (accounts[i].name.equals(defaultAccount.name)) {
                foundAccount = true;
            }
        }

        if (!foundAccount) {
            // 默认账户被注销了?
        }
    }

    public boolean hasActiveAccount() {
        return this.activeAccount != null;
    }

    public TesterUser getActiveAccountInfo() {
        if (null != activeAccount)
            return getAccountInfo(activeAccount);
        else
            return new TesterUser();
    }

    public Account getActiveAccount() {
        return this.activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account setActiveAccount(String username, String password) {
        Account account = findAccountByUsername(username);
        if (account == null) {
            account = new Account(username, mContext.getString(R.string.ACCOUNT_TYPE));
            mAccountManager.addAccountExplicitly(account, password, null);
        }
        this.activeAccount = account;

        return account;
    }

    public String getPassword(Account account) {
        return mAccountManager.getPassword(account);
    }

    public boolean signIn(String username, String password, TesterUser user) {

        if (user == null) {
            return false;
        }
        Account account = setActiveAccount(username, password);
        setDefaultAccount(account);

        updateAccountInfo(account, user);

        return true;
    }

    private TesterUser getAccountInfo(Account account) {
        TesterUser user = new TesterUser();
        user.setId(mAccountManager.getUserData(account, KEY_USER_DATA_USER_ID));
        user.setLogin(mAccountManager.getUserData(account, KEY_USER_DATA_ACCOUNT_NAME));
        user.setName(mAccountManager.getUserData(account, KEY_USER_DATA_NICKNAME));
        user.setAvatar_url(mAccountManager.getUserData(account, KEY_USER_DATA_AVATAR));
        user.setGithub(mAccountManager.getUserData(account, KEY_USER_DATA_GITHUB));
        user.setWebsite(mAccountManager.getUserData(account, KEY_USER_DATA_WEBSITE));
        user.setEmail(mAccountManager.getUserData(account, KEY_USER_DATA_EMAIL));
        user.setLocation(mAccountManager.getUserData(account, KEY_USER_DATA_LOCATION));
        user.setAccess_token(mAccountManager.getUserData(account, KEY_USER_DATA_TOKEN));
        return user;
    }

    public void updateActiveAccountInfo(TesterUser userProfile) {
        updateAccountInfo(activeAccount, userProfile);
    }

    public void updateAccountInfo(Account account, TesterUser userProfile) {
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_ID, String.valueOf(userProfile.getId()));
        mAccountManager.setUserData(account, KEY_USER_DATA_ACCOUNT_NAME, userProfile.getLogin());
        mAccountManager.setUserData(account, KEY_USER_DATA_NICKNAME, userProfile.getName());
        mAccountManager.setUserData(account, KEY_USER_DATA_AVATAR, userProfile.getAvatar_url());
        mAccountManager.setUserData(account, KEY_USER_DATA_GITHUB, userProfile.getGithub());
        mAccountManager.setUserData(account, KEY_USER_DATA_WEBSITE, userProfile.getWebsite());
        mAccountManager.setUserData(account, KEY_USER_DATA_EMAIL, userProfile.getEmail());
        mAccountManager.setUserData(account, KEY_USER_DATA_LOCATION, userProfile.getLocation());
        mAccountManager.setUserData(account, KEY_USER_DATA_TOKEN, userProfile.getAccess_token());
    }

    private Account findAccountByUsername(String username) {
        Log.d("tab", "findAccountByUsername, username: " + username);
        if (username.length() > 0) {
            for (Account account : mAccountManager.getAccountsByType(mContext.getString(R.string.ACCOUNT_TYPE))) {
                Log.d("tab", "findAccountByUsername, a.name: " + account.name);
                if (account.name.equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }

    public void logout() {
        Account account = getDefaultAccount();
        if (account != null) {
            mAccountManager.removeAccount(account, null, null);
            spfHelper.remove(KEY_DEFAULT_ACCOUNT);
            activeAccount = null;
        }
    }

    public void updateAccountAvatar( String avatar) {
        mAccountManager.setUserData(activeAccount, KEY_USER_DATA_AVATAR, avatar);
    }

}
