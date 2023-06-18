package com.mobile.binancedata.service

import com.mobile.binancedata.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    @GET("/api/v3/ticker/24hr")
    suspend fun getData() : Response<List<CryptoModel>>
}