package com.corootine.fuzzy.ui.matchmaking

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corootine.fuzzy.domain.userId.provide.UserIdProvider
import com.corootine.fuzzy.domain.userId.provide.UserIdProvider.UserIdState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import org.slf4j.LoggerFactory

class MatchmakingViewModel @ViewModelInject constructor(
    userIdProvider: UserIdProvider,
    private val okHttpClient: OkHttpClient
) : ViewModel() {

    private val logger = LoggerFactory.getLogger(javaClass)

    val userIdStateFlow: StateFlow<UserIdState> by lazy(LazyThreadSafetyMode.NONE) { userIdProvider.userIdStateFlow }
    val allowPlayFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    var partnerUserId: String = ""

    fun onPartnerUserIdChanged(userId: String) {
        partnerUserId = userId

        if (userId.length == 6) {
            allowPlayFlow.tryEmit(true)
        } else {
            allowPlayFlow.tryEmit(false)
        }
    }

    fun onPlayClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://192.168.0.2:8080/gamesession")
                .build()

            okHttpClient.newWebSocket(request, object : WebSocketListener() {

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    logger.debug("onClosed $code $reason")
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosing(webSocket, code, reason)
                    logger.debug("onClosing $code $reason")
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    logger.debug("onFailure $t $response")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    logger.debug("onMessage $text")
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    super.onMessage(webSocket, bytes)
                    logger.debug("onMessage $bytes")
                }

                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    logger.debug("onOpen $response")
                    webSocket.send("Hello")
                }
            })
        }
    }
}