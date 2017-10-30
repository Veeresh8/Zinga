package com.example.veeresh.zinga;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {


    private LoginViewModel loginViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    ZingaDatabase zingaDatabase;


    private FirebaseAuth firebaseAuth;


    private EditText edtName, edtPassword;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZingaApplication.getInstance().getZingaComponent().inject(this);

        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();

        edtName = (EditText) findViewById(R.id.edt_name);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        button = (Button) findViewById(R.id.done);


        getLifecycle()

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(edtName.getText().toString(), edtPassword.getText().toString()).observe(MainActivity.this, new Observer<com.example.veeresh.zinga.FirebaseUser>() {
                    @Override
                    public void onChanged(@Nullable com.example.veeresh.zinga.FirebaseUser firebaseUser) {
                        Toast.makeText(MainActivity.this, firebaseUser.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }
}
