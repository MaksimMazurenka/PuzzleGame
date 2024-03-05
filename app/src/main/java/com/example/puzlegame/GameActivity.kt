package com.example.puzlegame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.puzlegame.entity.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    private val handler = Handler()
    var buttonPressed =  false;
    var firstPressed = -1;
    var secondPressed = -1;
    var successCount = 0;
    var unpressed = true;
    private lateinit var prefs: SharedPreferences

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val rules = "Keep mathing up two of the same\n" +
                "object until there are no more to be\n" +
                "paired and you clear the board.\n" +
                "\n" +
                "fast"

        val game = Game();
        val textView3: TextView = findViewById(R.id.textView3) as TextView
        textView3.setText(rules)
        val textView: TextView = findViewById(R.id.textView) as TextView
        prefs = getSharedPreferences("com.example.puzlegame", Context.MODE_PRIVATE)
        textView.setText(prefs.getInt("money",0).toString())

        val timer = findViewById<Chronometer>(R.id.simpleChronometer)
        timer.start(); // start a chronometer

        for (i in 0..19) {
            val puzzle =  game.getArrElement(i);
            val buttonId = resources.getIdentifier("imageButton$i", "id", packageName)
            val button = findViewById<ImageButton>(buttonId)
            button.tag = i;
            button.setOnClickListener {
                if(unpressed){
                    var curId = button.tag;
                    var catId = game.getArrElement(curId as Int)
                    if(!buttonPressed){
                        buttonPressed = true;
                        firstPressed = curId as Int
                        mainScope.launch {
                            val imageId = resources.getIdentifier("cat$catId", "drawable", packageName)
                            asyncFunction(firstPressed, imageId)
                            setISClicked(firstPressed, false)
                        }
                    }else{
                        unpressed=false
                        secondPressed = curId as Int
                        buttonPressed = false
                        mainScope.launch {
                            val imageId = resources.getIdentifier("cat$catId", "drawable", packageName)
                            asyncFunction(secondPressed, imageId)
                            setISClicked(secondPressed, false)
                        }
                        if(game.getArrElement(secondPressed)==game.getArrElement(firstPressed)){
                            buttonPressed = false
                            val imageId = resources.getIdentifier("cat$catId", "drawable", packageName)
                            button.setImageResource(imageId)
                            successCount++
                            unpressed = true
                            if(successCount==10){
                                val time = SystemClock.elapsedRealtime() - timer.getBase()
                                var curScore = prefs.getInt("money",0)
                                var score = 0
                                val intent = Intent(this, WinActivity::class.java)
                                if(time/1000<20){
                                    prefs.edit().putInt("money",prefs.getInt("money",0)+100).apply()
                                    intent.putExtra("Prize", 100.toString())
                                }else{
                                    val leftMoney =  100 - (time/1000-20)*5
                                    if(leftMoney<10){
                                        score = curScore + 10
                                        intent.putExtra("Prize", 10.toString())
                                        prefs.edit().putInt("money",score).apply()
                                    }else{
                                        score = curScore + leftMoney.toInt()
                                        intent.putExtra("Prize", leftMoney.toString())
                                        prefs.edit().putInt("money",score).apply()
                                    }
                                }
                                startActivity(intent)
                            }
                        }else{
                            mainScope.launch {
                                val imageId = resources.getIdentifier("cat$catId", "drawable", packageName)
                                asyncFunction(secondPressed, imageId)
                                setISClicked(secondPressed, false)
                            }
                            handler.postDelayed({
                                mainScope.launch {
                                    val imageId = resources.getIdentifier("blue", "drawable", packageName)
                                    asyncFunction(secondPressed, imageId)
                                }
                                mainScope.launch {
                                    val imageId = resources.getIdentifier("blue", "drawable", packageName)
                                    asyncFunction(firstPressed, imageId)
                                }
                                setISClicked(firstPressed, true)
                                setISClicked(secondPressed, true)
                                unpressed = true
                            }, 500)

                        }
                    }
                }
            }
        }
    }

    private fun setISClicked(butId : Int, isClickable : Boolean) {
        val buttonId1 = resources.getIdentifier("imageButton$butId", "id", packageName)
        val button1 = findViewById<ImageButton>(buttonId1)
        button1.isClickable = isClickable
    }

    private suspend fun asyncFunction(butId : Int,imageId : Int) {
        val buttonId1 = resources.getIdentifier("imageButton$butId", "id", packageName)
        val button1 = findViewById<ImageButton>(buttonId1)
        button1.setImageResource(imageId)
        delay(100)
    }
}