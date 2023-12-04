package com.android.zulip.chat.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.zulip.chat.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(placeHolderString: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    placeHolderString,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxSize()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(10.dp)
                )
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}