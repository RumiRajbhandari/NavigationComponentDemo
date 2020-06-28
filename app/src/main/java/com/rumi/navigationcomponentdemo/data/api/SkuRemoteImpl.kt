package com.rumi.navigationcomponentdemo.data.api

import com.rumi.navigationcomponentdemo.model.SkuModel
import retrofit2.Response
import javax.inject.Inject

class SkuRemoteImpl @Inject constructor(private val apiService: ApiService) : SkuRemote {
    override suspend fun getSkus(): Response<List<SkuModel>> {
//        return apiService.getSkus()
        val skuList = mutableListOf<SkuModel>()
        skuList.add(SkuModel(1, "Ariel", 100f))
        skuList.add(SkuModel(2, "Head & Shoulder", 500f))
        skuList.add(SkuModel(3, "Kit Kat", 120f))
        skuList.add(SkuModel(4, "Gillette", 1000f))
        return Response.success(skuList)
    }
}