package com.omkar.jet2demo.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omkar.jet2demo.R
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if(savedInstanceState==null)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ArticleListFragment.newInstance()).commit()


    }

}
