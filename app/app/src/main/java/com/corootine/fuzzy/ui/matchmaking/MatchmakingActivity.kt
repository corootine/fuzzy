package com.corootine.fuzzy.ui.matchmaking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corootine.fuzzy.domain.userId.provide.UserIdProvider.UserIdState
import com.corootine.fuzzy.ui.theme.setFuzokuContent
import com.corootine.fuzzy.ui.widgets.NumberInput
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGameActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFuzokuContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                CreateGameScreen()
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun CreateGameScreen(viewModel: MatchmakingViewModel = viewModel()) {
    val userIdState = viewModel.userIdStateFlow.collectAsState(initial = UserIdState.Pending)
    val enablePlayButton = viewModel.allowPlayFlow.collectAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Give your friend this ID", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.heightIn(10.dp))
        when (userIdState.value) {
            is UserIdState.Available ->
                Text(
                    text = (userIdState.value as UserIdState.Available).userId.value,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight(500)
                    ),
                    letterSpacing = 3.sp,
                    color = MaterialTheme.colors.primary,
                )
            is UserIdState.Pending ->
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Enter their ID and play",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        NumberInput(
            modifier = Modifier.align(CenterHorizontally),
            length = 6,
            onInputChanged = viewModel::onPartnerUserIdChanged
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .width(300.dp)
                .height(40.dp)
                .align(CenterHorizontally),
            shape = RoundedCornerShape(18.dp),
            enabled = enablePlayButton.value,
            onClick = { viewModel.onPlayClicked() },
        ) {
            Text(text = "PLAY")
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun Preview() {
    CreateGameScreen()
}