package com.simon.rockpaperscissors.db

import androidx.room.TypeConverter
//import com.example.rockpaperscissors.model.Game
import java.util.*

class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

}