package com.example.upstox.feature.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.upstox.R
import com.example.upstox.feature.ui.fragment.FeedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flow

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, FeedFragment())
            .commit()

    }
}