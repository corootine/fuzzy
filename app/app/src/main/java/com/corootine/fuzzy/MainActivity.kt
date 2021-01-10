package com.corootine.fuzzy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.corootine.fuzzy.domain.appinstanceid.AppInstanceIdProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appInstanceIdProvider: AppInstanceIdProvider

    companion object {

        fun create(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val appInstanceId = appInstanceIdProvider.get()

            withContext(Dispatchers.Main) {
//                appId.text = appInstanceId
            }
        }
    }
}