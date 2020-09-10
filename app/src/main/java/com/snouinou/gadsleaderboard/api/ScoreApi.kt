package com.snouinou.gadsleaderboard.api

import com.snouinou.gadsleaderboard.model.Hours
import com.snouinou.gadsleaderboard.model.Skill
import retrofit2.Call;
import retrofit2.http.GET;



interface ScoreApi {
    @GET("api/hours")
    fun getTopLearners(): Call<ArrayList<Hours?>?>?

    @GET("api/skilliq")
    fun getTopSkillIQScores(): Call<ArrayList<Skill?>?>?
}