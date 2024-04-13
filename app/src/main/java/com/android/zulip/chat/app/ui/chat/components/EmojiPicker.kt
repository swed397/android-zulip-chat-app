package com.android.zulip.chat.app.ui.chat.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.domain.model.Emoji

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EmojiPicker(
    emojis: List<Emoji>,
    isPickerShow: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit
) {
    if (isPickerShow) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            content = {
                FlowRow(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    emojis.forEach {
                        println(it.emojiCode)
                        Text(
                            text = String(Character.toChars(it.emojiCode)),
                            fontSize = 30.sp
                        )
                    }
                }
            },
            modifier = Modifier.height(600.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun ChatBottomBarPreview() {
    EmojiPicker(
        listOf(Emoji(emojiCode = 1, emojiName = "test")),
        isPickerShow = true,
        onDismiss = {})
}