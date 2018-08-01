package com.skolimowskim.verificationtask

import android.app.IntentService
import android.content.Context
import android.content.Intent

class QueueService : IntentService("QueueService") {

    private val queue: ArrayList<String> = ArrayList()
    private var queueSize: Int = 0

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_INIT -> {
                queueSize = intent.getIntExtra(ARG_QUEUE_SIZE, 1)
            }
            ACTION_ADD -> {
                val item = intent.getStringExtra("QUEUE_ITEM")
                item?.let { queue.add(it) }
                if(queue.size > queueSize){
                    flush()
                }
            }
        }
    }

    private fun flush() {

    }

    companion object {
        private const val ACTION_INIT: String = "action_init"
        private const val ACTION_ADD: String = "action_add"
        private const val ARG_QUEUE_SIZE: String = "queue_size_arg"
        private const val ARG_QUEUE_ITEM: String = "queue_item_arg"

        fun initService(context: Context, queueSize: Int) {
            val intent = Intent(context, QueueService::class.java).apply {
                action = ACTION_INIT
                putExtra(ARG_QUEUE_SIZE, queueSize)
            }
            context.startService(intent)
        }

        fun addItemToList(context: Context, item: String) {
            val intent = Intent(context, QueueService::class.java).apply {
                action = ACTION_ADD
                putExtra(ARG_QUEUE_ITEM, item)
            }
            context.startService(intent)
        }
    }
}
