package com.example.homework2_2

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast

class Broadcast : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0,"I am here",Toast.LENGTH_LONG).show()
    }

    companion object{
        private const val RECEIVER_ACTION = "com.example.homework2_2"
        private const val REQUEST_CODE = 89

        val intentFilter = IntentFilter(RECEIVER_ACTION)

        fun getIntent(context: Context, title: String, info: String): Intent =
            Intent(context, Broadcast :: class.java).apply {
                action = RECEIVER_ACTION
                putExtra("EVENT_TITLE", title)
                putExtra("INFO_EVENT", info)

            }
        fun getAlarmPendingIntent(context: Context,intent: Intent): PendingIntent =
            PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}