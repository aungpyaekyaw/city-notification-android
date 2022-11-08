package com.nanoware.pakokkunotification.data.repository

import okhttp3.ResponseBody
import retrofit2.Response
import com.nanoware.pakokkunotification.data.model.NotificationModel
import com.nanoware.pakokkunotification.data.remote.FirebaseCloudMessagingApi

class FirebaseCloudMessagingRepository(
    private val api: FirebaseCloudMessagingApi
) {

    suspend fun postNotification(notification: NotificationModel): Response<ResponseBody> {
        return api.postNotification(notification)
    }
}