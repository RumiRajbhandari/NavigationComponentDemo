package com.rumi.navigationcomponentdemo.data.repository

import com.rumi.navigationcomponentdemo.data.remote.SkuRemoteImpl
import javax.inject.Inject

class CartRepository @Inject constructor(private val remoteImpl: SkuRemoteImpl) {
    suspend fun getCartList() = remoteImpl.getSkus()
}