package com.example.naran.showinmap.fcm;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.naran.showinmap.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NaRan on 5/8/17.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    public String token;

    @Override
    public void onTokenRefresh() {

        token = FirebaseInstanceId.getInstance().getToken();

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN), recent_token);

        editor.apply();
    }
}
