package com.rumi.navigationcomponentdemo.data.api

import com.rumi.navigationcomponentdemo.model.SkuModel
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {
    @GET("skus")
    suspend fun getSkus(): Response<List<SkuModel>>
}