package com.nanoware.pakokkunotification.data.remote

import com.nanoware.pakokkunotification.data.model.NotificationHistory
import com.nanoware.pakokkunotification.data.model.NotificationResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface BackendApi {

    companion object {
        const val BASE_URL = "https://city-notification.vercel.app/api/v1/"
        private const val CONTENT_TYPE = "application/json"
    }

    @GET("messages")
    suspend fun notifications(): Response<NotificationResponse>
}