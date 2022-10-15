package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.usecase.get_coin.GetCoinUseCase
import com.plcoding.cryptocurrencyappyt.domain.usecase.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*when we moved our BL to use case, why do we need viewmodel,
* the main purpose of our vm is to just maintain our state.when the app is rotated, config changes,
* they now contain less BL*/

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
     savedStateHandle:SavedStateHandle

):ViewModel() {
    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
         savedStateHandle.get<String>(Constants.PARAM_CONST_ID)?.let { coinId->
             getCoin(coinId)
         }
    }

    private fun getCoin(coinId:String){
        getCoinUseCase(coinId).onEach { result->
            when(result){
                is Resource.Success->{
                    _state.value = CoinDetailState(coin = result.data)

                }
                is Resource.Failure->{
                   _state.value = CoinDetailState(error = result.message?:"An unexpected error occurred")
                }
                is Resource.Loading->{
                    _state.value = CoinDetailState(isloading = true)
                }
            }

        }.launchIn(viewModelScope)
    }


}