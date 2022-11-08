package com.nanoware.pakokkunotification.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.nanoware.pakokkunotification.data.model.NotificationModel

interface FirebaseCloudMessagingApi {

    companion object {
        const val BASE_URL = "https://fcm.googleapis.com"
        private const val CONTENT_TYPE = "application/json"
    }

    @POST("fcm/send")
    @Headers("Authorization: key=", "Content-Type:$CONTENT_TYPE")
    suspend fun postNotification(
        @Body notification: NotificationModel
    ): Response<ResponseBody>

}