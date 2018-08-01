package com.skolimowskim.verificationtask

import android.app.IntentService
import android.content.Intent
import android.content.Context

class QueueService : IntentService("QueueService") {

    val itemsList: ArrayList<String> = ArrayList()

    override fun onHandleIntent(intent: Intent?) {
        val item = intent?.getStringExtra("QUEUE_ITEM")
        item?.let { itemsList.add(it) }
    }

    companion object {
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, QueueService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, QueueService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
    }
}
