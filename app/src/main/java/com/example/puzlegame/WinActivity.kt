package com.example.puzlegame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class WinActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private var doublet = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        prefs = getSharedPreferences("com.example.puzlegame", Context.MODE_PRIVATE)

        val doubleReward = findViewById(R.id.button) as Button
        val homeButton  =findViewById(R.id.imageButton20) as ImageButton
        val text  =findViewById(R.id.textView) as TextView
        val profileName = intent.getStringExtra("Prize")
        text.setText(profileName)

        doubleReward.setOnClickListener {
            if(!doublet){
                val itMoney = profileName?.toInt()
                val curResult = prefs.getInt("money",0)
                val doubletResult = curResult + itMoney!!
                prefs.edit().putInt("money",doubletResult).apply()
                doublet = true
                text.setText((itMoney*2).toString())
            }
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}