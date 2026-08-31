package com.example.strawberry_app.data.entity

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class StringListConverters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        if (value == null) return null
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        return Json.decodeFromString(value)
    }
}