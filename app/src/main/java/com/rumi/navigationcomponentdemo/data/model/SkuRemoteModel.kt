package com.rumi.navigationcomponentdemo.data.model

import com.google.gson.annotations.SerializedName

class SkuRemoteModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("price_unit") val price_unit: String,
    @SerializedName("image") val image: String
)