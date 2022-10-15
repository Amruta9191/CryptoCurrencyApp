package com.plcoding.cryptocurrencyappyt.domain.repository

import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetailDto
import com.plcoding.cryptocurrencyappyt.data.remote.dto.Coin_DtoItem
/*this interface hasn't much to do  with getting something from api,getting something from db,
* This interface will be very useful for test cases
* If we need to write test case which uses this repository, then you have have to run this test case for use case
* with actual repository which uses the api,that will take way to long
*
* This is Fake repository which simulates the behaviour of api and then this fake repository can also be used in test cases*/
interface CoinRepository {

    suspend fun getCoins():List<Coin_DtoItem>

    suspend fun getCoinsById(coinId:String):CoinDetailDto
}