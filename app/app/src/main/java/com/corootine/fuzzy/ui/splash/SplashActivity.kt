package com.corootine.fuzzy.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.corootine.fuzzy.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * First activity that's opened when the app is started.
 */
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel.userIdLiveData.observe(this, {
            Toast.makeText(this, it.id, LENGTH_SHORT).show()
        })
    }
}