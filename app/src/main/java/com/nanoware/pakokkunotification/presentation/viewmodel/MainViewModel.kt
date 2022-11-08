package com.nanoware.pakokkunotification.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.nanoware.pakokkunotification.core.constant.Notification
import com.nanoware.pakokkunotification.data.model.NotificationDataModel
import com.nanoware.pakokkunotification.data.model.NotificationHistory
import com.nanoware.pakokkunotification.data.model.NotificationModel
import com.nanoware.pakokkunotification.data.repository.BackendRepository
import com.nanoware.pakokkunotification.data.repository.FirebaseCloudMessagingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseCloudMessagingRepository,
    private val backendRepository: BackendRepository
) : ViewModel() {

    private var job: Job? = null

    var title by mutableStateOf("")
        private set

    var message by mutableStateOf("")
        private set

    var token by mutableStateOf("")
        private set

    fun onTitleChange(value: String) {
        title = value
    }

    fun onMessageChange(value: String) {
        message = value
    }

    fun onTokenChange(value: String) {
        token = value
    }

    private var _notificationStateData = MutableStateFlow<NotificationStateData>(NotificationStateData.NoData)
    val notificationStateData: StateFlow<NotificationStateData> = _notificationStateData

    fun getNotifications(){
        viewModelScope.launch {
            try{
                val response = backendRepository.notifications();
                if(response.isSuccessful){
                    Log.e("main view model",response.body()?.data?.count().toString());
                    val data = response.body()?.data
                    if(data != null)
                        _notificationStateData.value =NotificationStateData.Success(data)
                }
            }catch(e: Exception){
                Log.e("main view model","error when getting notifications ${e.message.toString()}")
            }
        }
    }

    fun onSendNotification() {
        if (title.isBlank() || message.isBlank()) return

        job?.cancel()
        job = viewModelScope.launch {
            try {
                val response = repository.postNotification(
                    NotificationModel(
                        data = NotificationDataModel(
                            title = title,
                            message = message
                        ),
                        to = token.ifBlank { Notification.TOPIC }
                    )
                )

                if (response.isSuccessful) {
                    title = ""
                    message = ""
                    token = ""

                    println("raheem: success!")
                } else {
                    println("raheem: noooo!")
                }

            } catch (e: HttpException) {
                println("raheem: ${e.message()}")
            }
        }
    }
}

sealed interface NotificationStateData {
    data class Success(val data: List<NotificationHistory>) : NotificationStateData
    object Error: NotificationStateData
    object NoData: NotificationStateData
}