package com.example.veeresh.zinga;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * Created by veeresh on 10/29/17.
 */

public class LoginRespository implements LifecycleObserver {


    private FirebaseAuth firebaseAuth;
    private Context context;

    @Inject
    public LoginRespository(FirebaseAuth firebaseAuth, Context context) {
        this.firebaseAuth = firebaseAuth;
        this.context = context;
    }


    public LiveData<FirebaseUser> performLogin(String username, String password) {

        final MutableLiveData<FirebaseUser> loginSuccessMutableLiveData = new MutableLiveData<>();

        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                FirebaseUser firebaseLoginSuccess = new FirebaseUser();

                com.google.firebase.auth.FirebaseUser user = firebaseAuth.getCurrentUser();

                firebaseLoginSuccess.setEmail(user.getEmail());
                firebaseLoginSuccess.setEmail(user.getUid());

                loginSuccessMutableLiveData.setValue(firebaseLoginSuccess);
            }
        });


        return loginSuccessMutableLiveData;
    }
}
