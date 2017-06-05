package com.nativoly.nativoly

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.ResultReceiver

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class AudioPlayerService : IntentService("AudioPlayerService") {



    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_PLAY == action) {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                val param3 = intent.getParcelableExtra<ResultReceiver>(EXTRA_PARAM3)
                handleActionFoo(param1, param2,param3)
            } /*else if (ACTION_BAZ == action) {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionBaz(param1, param2)
            }*/
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(lang: String, id: String,rr:ResultReceiver) {
        val mediaPlayer = MediaPlayer()

        val url = "https://audio.tatoeba.org/sentences/"+lang+"/"+id+".mp3"

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare() // might take long! (for buffering, etc)

        val b= Bundle()
        b.putInt("duration",mediaPlayer.duration)
        rr.send(204,b)

        mediaPlayer.start()
/*
        mediaPlayer.setOnCompletionListener(object: MediaPlayer.OnCompletionListener() {
            constructor(){}
            override fun onCompletion(mp: MediaPlayer?) {
                if(mp!=null)
                    mp.release()
            }
        })*/
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.

    private fun handleActionBaz(param1: String, param2: String) {
        // TODO: Handle action Baz
        throw UnsupportedOperationException("Not yet implemented")
    }
*/
    companion object {
        // TODO: Rename actions, choose action names that describe tasks that this
        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
        private val ACTION_PLAY = "com.nativoly.action.APS.PLAY"
        private val ACTION_BAZ = "com.nativoly.nativoly.action.BAZ"

        // TODO: Rename parameters
        private val EXTRA_PARAM1 = "com.nativoly.action.APS.LANG"
        private val EXTRA_PARAM2 = "com.nativoly.action.APS.ID"
        private val EXTRA_PARAM3 = "com.nativoly.action.APS.RECVR"

        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.

         * @see IntentService
         */
        // TODO: Customize helper method
        fun startActionPlay(context: Context, lang: String, id: String,rr: ResultReceiver) {
            val intent = Intent(context, AudioPlayerService::class.java)
            intent.action = ACTION_PLAY
            intent.putExtra(EXTRA_PARAM1, lang)
            intent.putExtra(EXTRA_PARAM2, id)
            intent.putExtra(EXTRA_PARAM3, rr)
            context.startService(intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.

         * @see IntentService

        // TODO: Customize helper method
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, AudioPlayerService::class.java)
            intent.action = ACTION_BAZ
            intent.putExtra(EXTRA_PARAM1, param1)
            intent.putExtra(EXTRA_PARAM2, param2)
            context.startService(intent)
        }*/
    }
}
