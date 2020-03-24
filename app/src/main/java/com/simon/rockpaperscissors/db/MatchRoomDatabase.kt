package com.simon.rockpaperscissors.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.simon.rockpaperscissors.model.Match

@Database(entities = [Match::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MatchRoomDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao

    companion object {
        private const val DATABASE_NAME = "MATCH_DATABASE"

        @Volatile
        private var gameRoomDatabaseInstance: MatchRoomDatabase? = null

        fun getDatabase(context: Context): MatchRoomDatabase? {
            if (gameRoomDatabaseInstance == null) {
                synchronized(MatchRoomDatabase::class.java) {
                    if (gameRoomDatabaseInstance == null) {
                        gameRoomDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                MatchRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return gameRoomDatabaseInstance
        }
    }
}