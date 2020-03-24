package com.simon.rockpaperscissors.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.rockpaperscissors.R
import com.simon.rockpaperscissors.db.MatchRepository
import com.simon.rockpaperscissors.model.Match
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryActivity : AppCompatActivity(){

    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val matches = arrayListOf<Match>()
    private val matchAdapter: MatchAdapter = MatchAdapter(matches)

    private lateinit var matchRepository: MatchRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        matchRepository = MatchRepository(this)
        initViews()
    }

    private fun initViews(){
        matchRepeater.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        matchRepeater.adapter = matchAdapter
        matchRepeater.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        getMatchesFromDatabase()
    }

    private fun getMatchesFromDatabase() {
        mainScope.launch {
            val gameList = withContext(Dispatchers.IO) {
                matchRepository.getAllGames()
            }
            this@HistoryActivity.matches.clear()
            this@HistoryActivity.matches.addAll(gameList)
            this@HistoryActivity.matchAdapter.notifyDataSetChanged()
        }
    }

    private fun removeHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                matchRepository.deleteAllGames()
            }
            getMatchesFromDatabase()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.remove_history -> {
                removeHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }
}