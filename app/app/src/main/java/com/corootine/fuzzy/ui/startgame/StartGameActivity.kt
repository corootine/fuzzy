package com.corootine.fuzzy.ui.startgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corootine.fuzzy.ui.theme.FuzokuTheme
import com.corootine.fuzzy.ui.theme.GreenA200
import com.corootine.fuzzy.ui.widgets.NumberInput
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
class CreateGameActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FuzokuTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    CreateGameScreen()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun CreateGameScreen(viewModel: StartGameViewModel = viewModel()) {
    val userIdState = viewModel.userId.observeAsState(initial = Result.Pending)
    val enableButton = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Enter your partner's ID below and connect to start a game",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        NumberInput(length = 6) {
            enableButton.value = it.length == 6
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(300.dp)
                .height(40.dp)
                .align(CenterHorizontally),
            shape = RoundedCornerShape(18.dp),
            enabled = enableButton.value,
            onClick = { },
        ) {
            Text(text = "CONNECT")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "- OR -", style = MaterialTheme.typography.overline.copy(fontSize = 12.sp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "have them enter yours", style = MaterialTheme.typography.h6)

        when (userIdState.value) {
            is Result.Success -> {
                Text(
                    text = (userIdState.value as Result.Success).userId,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight(500)
                    ),
                    letterSpacing = 3.sp,
                    color = GreenA200,
                )
            }
            else -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
