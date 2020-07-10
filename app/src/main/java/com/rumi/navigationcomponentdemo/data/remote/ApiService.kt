package com.rumi.navigationcomponentdemo.data.remote

import com.rumi.navigationcomponentdemo.data.model.SkuModel
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {
    @GET("skus")
    suspend fun getSkus(): Response<List<SkuModel>>
}