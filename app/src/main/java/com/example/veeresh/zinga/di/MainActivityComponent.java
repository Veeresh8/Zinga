package com.example.veeresh.zinga.di;


import dagger.Component;

/**
 * Created by veeresh on 10/25/17.
 */

@MainActivityScope
@Component (modules = MainActivityModule.class, dependencies = ZingaComponent.class)
public interface MainActivityComponent {


}
