package com.simon.rockpaperscissors.db

import android.content.Context
import com.simon.rockpaperscissors.model.Match

class MatchRepository(context: Context) {

    private val matchDao: MatchDao

    init {
        val database = MatchRoomDatabase.getDatabase(context)
        matchDao = database!!.matchDao()
    }


    suspend fun getAllGames(): List<Match> {
        return matchDao.getAllGames()
    }


    suspend fun insertGame(match: Match) {
        return matchDao.insertGame(match)
    }
    

    suspend fun deleteAllGames() {
        matchDao.deleteAllGames()
    }

    suspend fun getWins(): Int {
        return  matchDao.getWins()
    }

    suspend fun getDraws(): Int {
        return matchDao.getDraws()
    }

    suspend fun getLosses(): Int {
        return matchDao.getLosses()
    }
}