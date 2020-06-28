package com.rumi.navigationcomponentdemo.data.api

import com.rumi.navigationcomponentdemo.model.SkuModel
import retrofit2.Response

interface SkuRemote {
    suspend fun getSkus(): Response<List<SkuModel>>

}