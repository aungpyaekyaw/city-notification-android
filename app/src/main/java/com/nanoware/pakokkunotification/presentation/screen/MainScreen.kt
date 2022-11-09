package com.nanoware.pakokkunotification.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nanoware.pakokkunotification.presentation.viewmodel.MainViewModel
import com.nanoware.pakokkunotification.presentation.viewmodel.NotificationStateData

@Composable
fun MainScreen() {
    val viewmodel = hiltViewModel<MainViewModel>()


    viewmodel.getNotifications()

    val notificationState: NotificationStateData by viewmodel.notificationStateData.collectAsState()

    when(notificationState){
        is NotificationStateData.Success ->{
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
               items((notificationState as NotificationStateData.Success).data){
                   Column(
                       modifier = Modifier.padding(4.dp),
                       verticalArrangement = Arrangement.spacedBy(4.dp)
                   ){
                     NotificationCard(title = it.title, message = it.message, date = it.date)
                   }

               }
            }
        }
        is NotificationStateData.NoData -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }

        }
        else -> {
            Text(text = "Error")
        }

    }


    /*Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = viewmodel.title,
            onValueChange = viewmodel::onTitleChange,
            placeholder = {
                Text(text = "Title")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewmodel.message,
            onValueChange = viewmodel::onMessageChange,
            placeholder = {
                Text(text = "Message")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewmodel.token,
            onValueChange = viewmodel::onTokenChange,
            placeholder = {
                Text(text = "Token")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = viewmodel::onSendNotification) {
            Text(text = "Send notification")
        }
    }*/
}

@Composable
fun NotificationCard(
    title: String,
    message: String,
    date: String
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(text = date, fontSize = 10.sp)
            Text(text = title, fontSize = 16.sp)
            Text(text = message, fontSize = 14.sp)
        }
    }
}

@Preview
@Composable
fun PreviewNotificationCard(){
    NotificationCard(title = "Hello", message = "This is a test message.", date = "2022-11-09")
}