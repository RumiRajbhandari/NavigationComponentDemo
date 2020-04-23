package com.rumi.navigationcomponentdemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkuModel(
    var id: Int,
    var name: String,
    var price: Float
) : Parcelable