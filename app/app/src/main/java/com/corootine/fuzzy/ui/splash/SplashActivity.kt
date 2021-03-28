package com.corootine.fuzzy.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.corootine.fuzzy.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * First activity that's opened when the app is started.
 */
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}