package com.rumi.navigationcomponentdemo.data.repository

import com.rumi.navigationcomponentdemo.data.api.SkuRemoteImpl
import javax.inject.Inject

class HomeRepository @Inject constructor(private val remoteImpl: SkuRemoteImpl) {
    suspend fun getSkuList() = remoteImpl.getSkus()
}