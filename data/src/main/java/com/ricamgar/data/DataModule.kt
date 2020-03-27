package com.ricamgar.data

import android.app.Application
import androidx.room.Room
import com.ricamgar.data.local.AppDatabase
import com.ricamgar.data.local.RoomLocalDataSource
import com.ricamgar.data.remote.JsonPlaceholderApi
import com.ricamgar.data.remote.RetrofitRemoteDataSource
import com.ricamgar.domain.repository.datasource.LocalDataSource
import com.ricamgar.domain.repository.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(application: Application): LocalDataSource =
        RoomLocalDataSource(createDatabase(application))

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource =
        RetrofitRemoteDataSource(createApi())

    private fun createApi() =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)

    private fun createDatabase(application: Application) =
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app_database"
        ).build()
}
