package com.snouinou.gadsleaderboard.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.snouinou.gadsleaderboard.R
import com.snouinou.gadsleaderboard.model.Score
import java.net.URL


/**
 * A placeholder fragment containing a simple view.
 */
class ScoreListFragment : Fragment() {

    private lateinit var scoreListViewModel: ScoreListViewModel

    private lateinit var pageViewModel: PageViewModel
    private lateinit var scoreRecyclerView: RecyclerView
    private var adapter: ScoreAdapter? = null

    val TAG = "something"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoreListViewModel =
            ViewModelProviders.of(this).get(ScoreListViewModel::class.java).apply {
                setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            }
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        scoreRecyclerView =
            root.findViewById(R.id.score_recycler_view) as RecyclerView
        scoreRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return root
    }


    private fun updateUI() {
        scoreListViewModel.scores.observe(
            viewLifecycleOwner,
            Observer { scores ->
                scores.observe(viewLifecycleOwner,
                    Observer { scores ->
                        scoreRecyclerView.adapter = ScoreAdapter(scores)
                    })
            })
    }

    private inner class ScoreHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        private lateinit var score: Score
        private val titleTextView: TextView = itemView.findViewById(R.id.score_title)
        private val scoreTextView: TextView = itemView.findViewById(R.id.score_text)
        private val badgeImage: ImageView = itemView.findViewById(R.id.badge_image)

        fun bind(score: Score) {
            this.score = score
            titleTextView.text = this.score.getTitle()
            scoreTextView.text = this.score.getDesc()
            val newurl = URL(this.score.getImage())
            Glide.with(badgeImage)
                .load(newurl)
                .into(badgeImage);
        }
    }

    private inner class ScoreAdapter(var scores: List<Score>)
        : RecyclerView.Adapter<ScoreHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : ScoreHolder {
            val view = layoutInflater.inflate(R.layout.list_item_score, parent, false)
            return ScoreHolder(view)
        }
        override fun getItemCount() = scores.size
        override fun onBindViewHolder(holder: ScoreHolder, position: Int) {
            val score = scores[position]
            holder.bind(score)
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ScoreListFragment {
            return ScoreListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}