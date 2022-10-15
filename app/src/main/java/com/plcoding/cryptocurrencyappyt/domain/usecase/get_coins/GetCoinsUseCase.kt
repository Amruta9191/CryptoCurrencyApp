package com.plcoding.cryptocurrencyappyt.domain.usecase.get_coins

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
/*Use case should only have 1 public function*/

class GetCoinsUseCase @Inject constructor(
    private val repository:CoinRepository
){

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoins().map {
                it.toCoin()
            }
            emit(Resource.Success<List<Coin>>(coins))

        }
        catch (e:HttpException){
            emit(Resource.Failure<List<Coin>>(e.localizedMessage?:"Unexcepted error occurred"))
        }catch (e:IOException){
            emit(Resource.Failure<List<Coin>>("No internet connection"))

        }
    }
}