package com.snouinou.gadsleaderboard.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.EditText
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.snouinou.gadsleaderboard.R
import com.snouinou.gadsleaderboard.ui.login.SubmitFormActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

    fun submitForm(view: View?) {
        val intent = Intent(this, SubmitFormActivity::class.java)
        startActivity(intent)
    }
}