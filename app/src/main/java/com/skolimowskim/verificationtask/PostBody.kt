package com.skolimowskim.verificationtask

import com.google.gson.annotations.SerializedName

class PostBody(@SerializedName("items") val queue: ArrayList<String> = ArrayList())
