package com.snouinou.gadsleaderboard.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.snouinou.gadsleaderboard.model.Hours
import com.snouinou.gadsleaderboard.model.Skill
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "ScoreFetch"

class ScoreFetch {

    private val scoreApi: ScoreApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://gadsapi.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        scoreApi = retrofit.create(ScoreApi::class.java)
    }

    fun fetchHours(): LiveData<List<Hours>> {
        val responseLiveData: MutableLiveData<List<Hours>> = MutableLiveData()

        scoreApi.getTopLearners()?.enqueue(object : Callback<ArrayList<Hours?>?> {

            override fun onFailure(call: Call<ArrayList<Hours?>?>, t: Throwable) {
                Log.e(TAG, "Failed to fetch", t)
            }

            override fun onResponse(call: Call<ArrayList<Hours?>?>, response: Response<ArrayList<Hours?>?>) {
                Log.d(TAG, "Response received")
                val scoreResponse: ArrayList<Hours?>? = response.body()
                responseLiveData.value = scoreResponse?.toList() as List<Hours>?
                Log.d(TAG, "Response received: ${response.body()}")
                Log.d(TAG, "Response received: ${responseLiveData.value}")
            }
        })

        return responseLiveData
    }

    fun fetchSkill(): LiveData<List<Skill>> {
        val responseLiveData: MutableLiveData<List<Skill>> = MutableLiveData()

        scoreApi.getTopSkillIQScores()?.enqueue(object : Callback<ArrayList<Skill?>?> {

            override fun onFailure(call: Call<ArrayList<Skill?>?>, t: Throwable) {
                Log.e(TAG, "Failed to fetch", t)
            }

            override fun onResponse(call: Call<ArrayList<Skill?>?>, response: Response<ArrayList<Skill?>?>) {
                Log.d(TAG, "Response received")
                val scoreResponse: ArrayList<Skill?>? = response.body()
                responseLiveData.value = scoreResponse?.toList() as List<Skill>?
                Log.d(TAG, "Response received: ${response.body()}")
                Log.d(TAG, "Response received: ${responseLiveData.value}")
            }
        })

        return responseLiveData
    }
}