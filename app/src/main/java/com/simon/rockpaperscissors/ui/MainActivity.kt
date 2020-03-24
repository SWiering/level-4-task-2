package com.simon.rockpaperscissors.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.simon.rockpaperscissors.R
import com.simon.rockpaperscissors.db.MatchRepository
import com.simon.rockpaperscissors.model.Match
import com.simon.rockpaperscissors.model.Move

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.match_result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var matchRepository: MatchRepository
    private lateinit var context: Context

    private val move = Move()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        context = this
        matchRepository = MatchRepository(this)

        initViews()
    }

    private fun initViews(){
        getStatsFromDatabase()
        imageRock.setOnClickListener{ setMove(move.rockMove) }
        imagePaper.setOnClickListener{ setMove(move.paperMove) }
        imageScissors.setOnClickListener{ setMove(move.scissorsMove) }
    }

    private fun getStatsFromDatabase(){
        mainScope.launch {
            var wins = 0
            var draws = 0
            var losses = 0
            withContext(Dispatchers.IO){
                wins = matchRepository.getWins()
                draws = matchRepository.getDraws()
                losses = matchRepository.getLosses()
            }
            statisticsPlaceholder.text = context.getString(R.string.statistics, wins, draws, losses)
        }
    }

    private fun setMove(moveIndex: Int){
        val randomMove = (0..2).random()

        // set the images for the choices
        choiceUser.setImageResource(move.getMoveImage(moveIndex))
        choiceComputer.setImageResource(move.getMoveImage(randomMove))

        val theResult = getMoveResult(randomMove, moveIndex)

        val matchResult = Match(
            choice_user = moveIndex,
            choice_cpu = randomMove,
            date = Date(),
            result = theResult
        )
        matchTitle.text = theResult

        addMatchToDatabase(matchResult)

        getStatsFromDatabase()
    }

    private fun getMoveResult(choiceIndexComputer: Int, choiceIndexUser: Int): String{
        return if(choiceIndexComputer == choiceIndexUser){
            context.getString(R.string.draw)
        } else if((choiceIndexComputer == move.rockMove && choiceIndexUser == move.paperMove) ||
            (choiceIndexComputer == move.paperMove && choiceIndexUser == move.scissorsMove) ||
            (choiceIndexComputer == move.scissorsMove && choiceIndexUser == move.rockMove)){
            context.getString(R.string.win)
        } else{
            context.getString(R.string.lose)
        }
    }

    private fun addMatchToDatabase(match: Match) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                matchRepository.insertGame(match)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_show_history -> {
                startHistoryActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}
