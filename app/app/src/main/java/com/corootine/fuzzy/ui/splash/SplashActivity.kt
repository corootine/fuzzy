package com.corootine.fuzzy.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corootine.fuzzy.ui.startgame.CreateGameActivity
import com.corootine.fuzzy.ui.theme.FuzokuTheme
import com.corootine.fuzzy.ui.theme.GreenA200
import com.corootine.fuzzy.ui.widgets.ScalingCircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint

/**
 * First activity that's opened when the app is started.
 */
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
        }
    }

    @Preview
    @Composable
    fun SplashScreen(splashViewModel: SplashViewModel = viewModel()) {
        val result: Result by splashViewModel.userIdFetchLiveData.observeAsState(Result.Pending)
        val context = LocalContext.current

        when (result) {
            is Result.Failed -> Toast.makeText(context, "Failed to connect.", LENGTH_SHORT).show()
            is Result.Success -> startActivity(Intent(context, CreateGameActivity::class.java))
            is Result.Pending -> {
                FuzokuTheme {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ScalingCircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            color = GreenA200
                        )
                    }
                }
            }
        }
    }
}