package com.rumi.navigationcomponentdemo.data.remote

import com.rumi.navigationcomponentdemo.data.model.SkuModel
import com.rumi.navigationcomponentdemo.data.model.SkuRemoteModel
import retrofit2.Response

interface SkuRemote {
    suspend fun getSkus(): Response<List<SkuRemoteModel>>

}