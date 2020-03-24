package com.simon.rockpaperscissors.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.simon.rockpaperscissors.model.Match

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches")
    suspend fun getAllGames(): List<Match>

    @Insert
    suspend fun insertGame(match: Match)

    @Query("DELETE FROM matches")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(result) FROM matches WHERE result = \"win\"")
    suspend fun getWins(): Int

    @Query("SELECT COUNT(result) FROM matches WHERE result = \"draw\"")
    suspend fun getDraws(): Int

    @Query("SELECT COUNT(result) FROM matches WHERE result = \"lose\"")
    suspend fun getLosses(): Int
}