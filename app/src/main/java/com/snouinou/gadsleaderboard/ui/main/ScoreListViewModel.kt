package com.snouinou.gadsleaderboard.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.snouinou.gadsleaderboard.api.ScoreFetch
import com.snouinou.gadsleaderboard.model.Hours
import com.snouinou.gadsleaderboard.model.Score
import com.snouinou.gadsleaderboard.model.Skill

class ScoreListViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }

    val scores: LiveData<LiveData<List<Score>>> = Transformations.map(_index) {
        if(it == 1){
            return@map ScoreFetch().fetchHours() as LiveData<List<Score>>
        } else {
            return@map ScoreFetch().fetchSkill() as LiveData<List<Score>>
        }
    }

}