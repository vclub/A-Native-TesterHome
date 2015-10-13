package com.testerhome.nativeandroid.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by vclub on 15/10/13.
 */
public class UserAccountService extends Service {
    private UserAccountAuthenticator _saa;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder ret = null;
        if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
            ret = getTesterAuthenticator().getIBinder();
        return ret;
    }

    private UserAccountAuthenticator getTesterAuthenticator() {
        if (_saa == null)
            _saa = new UserAccountAuthenticator(this);
        return _saa;
    }
}
