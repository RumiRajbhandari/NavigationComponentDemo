package com.rumi.navigationcomponentdemo.data.mapper

import com.rumi.navigationcomponentdemo.data.model.SkuModel
import com.rumi.navigationcomponentdemo.data.model.SkuRemoteModel

object SkuMapper {
    fun mapToDomain(skus: List<SkuRemoteModel>?): List<SkuModel>? {
        return skus?.map { SkuModel(it.id, it.name, it.price, it.price_unit, it.image) }
    }
}