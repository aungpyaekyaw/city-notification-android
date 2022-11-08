package com.nanoware.pakokkunotification.data.repository

import com.nanoware.pakokkunotification.data.model.NotificationHistory
import com.nanoware.pakokkunotification.data.model.NotificationResponse
import com.nanoware.pakokkunotification.data.remote.BackendApi
import okhttp3.ResponseBody
import retrofit2.Response

class BackendRepository(
    private val api: BackendApi
) {

    suspend fun notifications(): Response<NotificationResponse> {
        return api.notifications()
    }
}