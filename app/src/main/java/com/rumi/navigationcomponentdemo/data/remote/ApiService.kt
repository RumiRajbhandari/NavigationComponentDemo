package com.rumi.navigationcomponentdemo.data.remote

import com.rumi.navigationcomponentdemo.data.model.SkuRemoteModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("skus")
    suspend fun getSkus(): Response<List<SkuRemoteModel>>
}