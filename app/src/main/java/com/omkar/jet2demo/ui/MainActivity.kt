package com.omkar.jet2demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omkar.jet2demo.R
import dagger.android.AndroidInjection
import io.realm.RealmObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,ArticleListFragment.newInstance()).commit()

    }

}
