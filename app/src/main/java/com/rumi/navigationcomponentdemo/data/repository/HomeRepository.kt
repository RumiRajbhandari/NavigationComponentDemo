package com.rumi.navigationcomponentdemo.data.repository

import com.rumi.navigationcomponentdemo.data.model.SkuRemoteModel
import com.rumi.navigationcomponentdemo.data.remote.SkuRemoteImpl
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(private val remoteImpl: SkuRemoteImpl) {
    suspend fun getSkuList(): Response<List<SkuRemoteModel>> = remoteImpl.getSkus()
}