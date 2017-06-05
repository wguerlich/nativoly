package com.nativoly.nativoly

import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.ResultReceiver
import android.view.MotionEvent
import android.view.View
import android.widget.Toast





class MainActivity : AppCompatActivity() {
    val sentenceProvider=SentenceProvider()
    var s:Sentence?=null

    val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val sentenceFile = getResources().openRawResource(R.raw.spa)
        val langs= listOf("deu","eng")
        sentenceProvider.load(sentenceFile,langs)

        Toast.makeText(applicationContext, ""+sentenceProvider.count+ " sentences", Toast.LENGTH_SHORT).show()

        fun nextSentence()
        {
            val s=sentenceProvider.next()
            this.s=s

            val sv = findViewById(R.id.sentenceView) as TextView

            sv.text=s.text

            val tv = findViewById(R.id.translationView) as TextView
            tv.text=s.translation

        }

        val rr= object:ResultReceiver(Handler())
        {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                if(resultData==null) return
                Toast.makeText(applicationContext, "duration "+resultData["duration"], Toast.LENGTH_SHORT).show()

            }
        }





        fun playAudio()
        {
            val s=this.s
            if(s==null) return
            val url = "https://audio.tatoeba.org/sentences/"+s.lang+"/"+s.id+".mp3"

            mediaPlayer.reset()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare() // might take long! (for buffering, etc)

            mediaPlayer.start()

        }

        /*
        fun playAudio()
        {
         val s=this.s
            if(s==null) return
            AudioPlayerService.startActionPlay(this,s.lang,s.id,rr)

        }
         */

        val cv = findViewById(R.id.main_content)
        cv.setOnTouchListener(object: SwipeGestureListener(this) {
            override fun onSwipeLeft() {
                nextSentence()
                playAudio()
            }

            override fun onClick() {
                playAudio()
            }

        })

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
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
