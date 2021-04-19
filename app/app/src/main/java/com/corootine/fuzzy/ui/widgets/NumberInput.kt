package com.corootine.fuzzy.ui.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import okhttp3.internal.toImmutableList

@ExperimentalComposeUiApi
@Composable
fun NumberInput(length: Int) {
    val session = remember { mutableStateOf(NumberInputSession(length)) }
    val inputs by session.value.inputs.observeAsState()
    val focus by session.value.focus.observeAsState()

    val textInputService = LocalTextInputService.current
    val editProcessor = EditProcessor()

    if (session.value.hasFocusedInput()) {
        if (textInputService != null) {
            val textInputSession = textInputService.startInput(
                imeOptions = ImeOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                value = TextFieldValue(""),
                onEditCommand = { commands ->
                    val text = editProcessor.apply(commands).text
                    val textToPass = if (text.isEmpty()) "" else text.last().toString()
                    session.value.onTextChange(textToPass)
                },
                onImeActionPerformed = {
                    session.value.onImeActionPerformed()
                }
            )
            session.value.onStartInput(textInputSession)
        }
    } else {
        session.value.onStopInput()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(horizontal = 20f.dp, vertical = 30f.dp)),
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until length) {
            NumberInputField(
                focus = focus?.get(i) ?: false,
                value = inputs?.get(i) ?: "",
            ) { focusState ->
                session.value.onFocusChange(i, focusState)
            }
            Spacer(modifier = Modifier.width(10f.dp))
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun NumberInputField(
    focus: Boolean,
    value: String,
    onFocusChange: (FocusState) -> Unit,
) {
    val focusRequester = FocusRequester()
    val borderWidth: Dp
    val borderColor: Color
    val fontSize: TextUnit
    val paddingTop: Dp

    when (focus) {
        true -> {
            borderWidth = 3f.dp
            borderColor = MaterialTheme.colors.onBackground
            fontSize = 26.sp
            paddingTop = 0.dp
        }
        false -> {
            borderWidth = 2f.dp
            borderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
            fontSize = 24.sp
            paddingTop = 3.dp
        }
    }

    Box(
        modifier = Modifier
            .width(35.dp)
            .padding(top = paddingTop)
            .border(
                borderWidth, SolidColor(borderColor),
                RoundedCornerShape(6f.dp)
            )
            .clickable(enabled = true, indication = null, interactionSource = MutableInteractionSource()) {
                focusRequester.requestFocus()
            }
            .onFocusChanged { onFocusChange(it) }
            .focusRequester(focusRequester)
            .focusable(enabled = true, interactionSource = null)
            .animateContentSize()
    ) {
        Text(
            modifier = Modifier.padding(10f.dp + borderWidth),
            text = value,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            style = MaterialTheme.typography.button
        )
    }
    DisposableEffect(focus) {
        if (focus) {
            focusRequester.requestFocus()
        }
        onDispose { }
    }
}

private class NumberInputSession(private val length: Int) {

    private val numberRegex = Regex("\\d")
    private val empty = ""

    private var textInputSession: TextInputSession? = null

    val inputs = MutableLiveData((0 until length).map { "" })
    val focus = MutableLiveData((0 until length).map { false })

    fun onFocusChange(index: Int, focusState: FocusState) {
        val currentFocus = focus.value?.toMutableList()
        requireNotNull(currentFocus)
        currentFocus[index] = focusState == FocusState.Active
        focus.value = currentFocus.toImmutableList()
    }

    fun onTextChange(number: String) {
        if (number.matches(numberRegex)) {
            val currentFocus = focus.value?.toMutableList()
            val currentInputs = inputs.value?.toMutableList()

            requireNotNull(currentFocus)
            requireNotNull(currentInputs)

            val currentFocusedField = currentFocus.indexOf(true)

            currentInputs[currentFocusedField] = number
            currentFocus[currentFocusedField] = false

            if (currentFocusedField + 1 < length) {
                currentFocus[currentFocusedField + 1] = true
            }

            inputs.value = currentInputs.toImmutableList()
            focus.value = currentFocus.toImmutableList()
        } else if (number.isEmpty()) {
            val currentFocus = focus.value?.toMutableList()
            val currentInputs = inputs.value?.toMutableList()

            requireNotNull(currentFocus)
            requireNotNull(currentInputs)

            val currentFocusedField = currentFocus.indexOf(true)

            currentInputs[currentFocusedField] = empty

            if (currentFocusedField - 1 >= 0) {
                currentFocus[currentFocusedField] = false
                currentFocus[currentFocusedField - 1] = true
            }

            inputs.value = currentInputs.toImmutableList()
            focus.value = currentFocus.toImmutableList()
        }
    }

    fun hasFocusedInput() = focus.value?.any { it } ?: false

    fun onImeActionPerformed() {
        val currentFocus = focus.value?.toMutableList()
        requireNotNull(currentFocus)
        currentFocus.replaceAll { false }
        focus.value = currentFocus.toImmutableList()
    }

    fun onStartInput(textInputSession: TextInputSession) {
        this.textInputSession = textInputSession
    }

    fun onStopInput() {
        textInputSession?.hideSoftwareKeyboard()
        textInputSession = null
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun UserIdInputPreview() {
    NumberInput(4)
}
