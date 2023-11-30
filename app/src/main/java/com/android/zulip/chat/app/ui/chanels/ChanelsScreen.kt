package com.android.zulip.chat.app.ui.chanels

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.ui.people.PeopleScreen
import com.android.zulip.chat.app.ui.profile.ProfileScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChannelsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
    ) {
        SearchBar()
        ChanelTabs()
    }
}

@Composable
fun BottomNavigationBar() {
    val selectedItem = remember { mutableStateOf(0) }
    val items = listOf("Channels", "People", "Profile")
    val icons = listOf(
        R.drawable.baseline_headset_mic_24,
        R.drawable.baseline_people_24,
        R.drawable.baseline_self_improvement_24
    )

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Channels") {
        composable("Channels") { ChannelsScreen() }
        composable("People") { PeopleScreen() }
        composable("Profile") { ProfileScreen() }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = { navController.navigate(items[index]) },
//                onClick = {  },
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]),
                        contentDescription = item
                    )
                })
        }
    }
}

@Composable
fun StreamsList() {
    val data = remember {
        listOf("1 item", "2 item", "3 item", "4 item", "5 item").toMutableStateList()
    }
    LazyColumn {
        items(data) {
            StreamListItem(it)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun StreamListItem(text: String) {
    var expanded by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Color.Black)
            .animateContentSize()
            .height(if (expanded) 60.dp else 120.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded = !expanded
            }
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.Green,
            modifier = Modifier
                .fillMaxSize()
                .weight(9f)
                .wrapContentHeight()
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
}

@Composable
fun ChanelTabs() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Subscribed", "All")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> AllChannels()
            1 -> SubscribedChannels()
        }
    }
}

@Composable
fun AllChannels() {
    StreamsList()
}

@Composable
fun SubscribedChannels() {
    Text(text = "SUBSCRIBED")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
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
                    "Search",
                    color = Color.Gray,
                    modifier = Modifier.fillMaxSize()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
                .weight(9f)
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
}


@Composable
@Preview
fun ChannelPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
    ) {
        SearchBar()
        ChanelTabs()
    }
}