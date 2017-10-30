package com.example.veeresh.zinga;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

/**
 * Created by veeresh on 10/29/17.
 */

public class LoginViewModel extends ViewModel {


    private String name, password;
    private LoginRespository loginRespository;

    @Inject
    public LoginViewModel(LoginRespository loginRespository){
        this.loginRespository = loginRespository;
    }

    public LiveData<FirebaseUser> login(String username, String password){
        return loginRespository.performLogin(username, password);
    }



}
