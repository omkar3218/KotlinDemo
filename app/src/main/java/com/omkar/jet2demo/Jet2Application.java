package com.omkar.jet2demo;

import android.app.Application;

import com.omkar.jet2demo.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class Jet2Application extends DaggerApplication {


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
         return DaggerAppComponent.builder().application(this).build();
    }
}
