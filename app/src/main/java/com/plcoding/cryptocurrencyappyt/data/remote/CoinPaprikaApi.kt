package com.plcoding.cryptocurrencyappyt.data.remote

import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetailDto
import com.plcoding.cryptocurrencyappyt.data.remote.dto.Coin_DtoItem
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins():List<Coin_DtoItem>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId")coinId:String):CoinDetailDto


}