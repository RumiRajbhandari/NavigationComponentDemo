package com.rumi.navigationcomponentdemo.data.remote

import com.rumi.navigationcomponentdemo.data.model.SkuRemoteModel
import retrofit2.Response
import javax.inject.Inject

class CartRemoteImpl @Inject constructor(private val apiService: ApiService) : CartRemote {
    /*override suspend fun getSkus(): Response<List<SkuRemoteModel>> {
        return apiService.getSkus()
    }*/
}