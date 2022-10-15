package com.plcoding.cryptocurrencyappyt.domain.usecase.get_coin

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetailDto
import com.plcoding.cryptocurrencyappyt.data.remote.dto.Coin_DtoItem
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
/*Use case should only have 1 public function*/

class GetCoinUseCase @Inject constructor(
    private val repository:CoinRepository
){

    operator fun invoke(coinId:String):Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinsById(coinId).toCoinDetail()
            emit(Resource.Success<CoinDetail>(coin))
        }
        catch (e:HttpException){
            emit(Resource.Failure<CoinDetail>(e.localizedMessage?:"Unexcepted error occurred"))

        }
        catch (e:IOException){
            emit(Resource.Failure<CoinDetail>("No internet connection"))

        }
    }

   /* operator fun invoke(coinId:String): Flow<Resource<CoinDetailDto>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoinsById(coinId).toCoinDetail()


            emit(Resource.Success(coins))

        }
        catch (e:HttpException){
            emit(Resource.Failure(e.localizedMessage?:"Unexcepted error occurred"))
        }catch (e:IOException){
            emit(Resource.Failure("No internet connection"))

        }
    }*/
}