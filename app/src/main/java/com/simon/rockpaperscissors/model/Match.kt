package com.simon.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "matches")
data class Match(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "choice_user")
    var choice_user: Int,

    @ColumnInfo(name = "choice_cpu")
    var choice_cpu: Int,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "result")
    var result: String

) : Parcelable

