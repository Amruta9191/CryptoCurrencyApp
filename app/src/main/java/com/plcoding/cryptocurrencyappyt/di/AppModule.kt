package com.plcoding.cryptocurrencyappyt.di

import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.data.remote.CoinPaprikaApi
import com.plcoding.cryptocurrencyappyt.data.repository.CoinRepositoryImpl
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

/*The whole purpose od DI is , it helps us to replace our dependencies, so dependency at the end is just and object
 in our app, eg db instance, api instance,glide instance,coroutine dispatchers, and the thing we need to avoid is
 hardcoding this dependencies into our object. So in Use case class if we write repository = ......
 then it will not be replaceable, if we need to replace repository with fake repository
 @InstallIn will determine how long this dependency will live. Singleton means it will live as long as our application lives
* */
@Module
@InstallIn(SingletonComponent::class)//all the dependency in module will live as long as app does
object AppModule {

    @Provides// this will provide the dependency
    @Singleton//it will make sure we have single instance of whatever the function returns
    fun providesPaprikaApi():CoinPaprikaApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api:CoinPaprikaApi):CoinRepository{
        return CoinRepositoryImpl(api)
    }

}