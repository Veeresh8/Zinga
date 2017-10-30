package com.example.veeresh.zinga;

/**
 * Created by veeresh on 10/29/17.
 */

public class FirebaseUser {


   private String name;
   private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "FirebaseUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
