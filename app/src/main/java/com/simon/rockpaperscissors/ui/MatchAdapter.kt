package com.simon.rockpaperscissors.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simon.rockpaperscissors.R
import com.simon.rockpaperscissors.model.Match
import com.simon.rockpaperscissors.model.Move
import kotlinx.android.synthetic.main.match_result.view.*

class MatchAdapter(private val games: List<Match>) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    lateinit var context : Context
    val MOVE = Move()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.match_result, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    inner class ViewHolder (itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(match: Match) {
            itemView.matchTitle.text = match.result
            itemView.matchDateString.text = match.date.toString()
            itemView.choiceComputer.setImageResource(MOVE.getMoveImage(match.choice_cpu))
            itemView.choiceUser.setImageResource(MOVE.getMoveImage(match.choice_user))
        }
    }
}
