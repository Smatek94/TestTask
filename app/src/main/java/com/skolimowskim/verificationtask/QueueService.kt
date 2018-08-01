package com.skolimowskim.verificationtask

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.skolimowskim.verificationtask.utils.LogUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QueueService : IntentService("QueueService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_INIT -> {
                queueSize = intent.getIntExtra(ARG_QUEUE_SIZE, 1)
                url = intent.getStringExtra(ARG_URL)
                LogUtils.log("set queue size to : " + queueSize)
            }
            ACTION_ADD -> {
                val item = intent.getStringExtra(ARG_QUEUE_ITEM)
                item?.let { queue.add(it) }
                LogUtils.log("item added, queueSize : " + queue.size + " max queue size : " + queueSize)
                if(queue.size >= queueSize){
                    flush()
                }
            }
        }
    }

    private fun flush() {
        val retrofit = Retrofit.Builder() // should be injected with dagger2, not initialized every time
                .baseUrl("https://www.google.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val queueService = retrofit.create(QueueApi::class.java)
        queueService.postQueue(url, PostBody(queue))
        LogUtils.log("flushing items : " + queue.size)
        queue.clear()
    }

    companion object {
        private var url: String = ""
        private var queueSize: Int = 0
        private val queue: ArrayList<String> = ArrayList()

        private const val ACTION_INIT: String = "action_init"
        private const val ACTION_ADD: String = "action_add"
        private const val ARG_QUEUE_SIZE: String = "queue_size_arg"
        private const val ARG_QUEUE_ITEM: String = "queue_item_arg"
        private const val ARG_URL: String = "url_arg"

        fun initService(context: Context, queueSize: Int, url: String) {
            val intent = Intent(context, QueueService::class.java).apply {
                action = ACTION_INIT
                putExtra(ARG_QUEUE_SIZE, queueSize)
                putExtra(ARG_URL, url)
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
