package com.skolimowskim.verificationtask

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface QueueApi {

    @POST
    fun postQueue(@Url url: String, @Body postBody: PostBody) : Call<Throwable> // should be some response class instead of throwable

}