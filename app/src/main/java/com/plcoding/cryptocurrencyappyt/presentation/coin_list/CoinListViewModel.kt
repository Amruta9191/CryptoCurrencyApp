package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.usecase.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*when we moved our BL to use case, why do we need viewmodel,
* the main purpose of our vm is to just maintain our state.when the app is rotated, config changes,
* they now contain less BL*/

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
):ViewModel() {
    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins(){
        getCoinsUseCase().onEach { result->
            when(result){
                is Resource.Success->{
                    _state.value = CoinListState(coins = result.data?: emptyList())

                }
                is Resource.Failure->{
                   _state.value = CoinListState(error = result.message?:"An unexpected error occurred")
                }
                is Resource.Loading->{
                    _state.value = CoinListState(isloading = true)
                }
            }

        }.launchIn(viewModelScope)
    }


}