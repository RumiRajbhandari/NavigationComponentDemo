package com.rumi.navigationcomponentdemo.data.remote

import com.rumi.navigationcomponentdemo.data.model.SkuModel
import retrofit2.Response

interface CartRemote {
    suspend fun getSkus(): Response<List<SkuModel>>

}