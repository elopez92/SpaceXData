package com.elopez.spacexdata

import android.os.Bundle
import androidx.navigation.NavType
import com.elopez.spacexdata.model.LaunchDataItem
import com.google.gson.Gson

class AssetParamType: NavType<LaunchDataItem>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): LaunchDataItem? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): LaunchDataItem {
        return Gson().fromJson(value, LaunchDataItem::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: LaunchDataItem) {
        bundle.putParcelable(key, value)
    }
}