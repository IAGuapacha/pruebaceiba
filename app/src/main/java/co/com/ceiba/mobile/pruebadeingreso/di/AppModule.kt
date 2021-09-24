package co.com.ceiba.mobile.pruebadeingreso.di

import android.content.Context
import androidx.room.Room
import co.com.ceiba.mobile.pruebadeingreso.application.AppConstans
import co.com.ceiba.mobile.pruebadeingreso.data.local.AppDatabase
import co.com.ceiba.mobile.pruebadeingreso.data.local.LocalDataSource
import co.com.ceiba.mobile.pruebadeingreso.data.local.PostDao
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserDao
import co.com.ceiba.mobile.pruebadeingreso.data.remote.RemoteDataSource
import co.com.ceiba.mobile.pruebadeingreso.data.remote.WebService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "users_database").build()

    @Singleton
    @Provides
    fun provideUserDao(db:AppDatabase) = db.userDao()


    @Singleton
    @Provides
    fun providePostDao(db:AppDatabase) = db.postDao()


    @Singleton
    @Provides
    fun provideWebService(): WebService {
        return Retrofit.Builder()
            .baseUrl(AppConstans.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }

    @Provides
    fun provideRemoteDataSource(webService: WebService) = RemoteDataSource(webService)

    @Provides
    fun provideLocalDataSource(userDao:UserDao,postDao:PostDao) = LocalDataSource(userDao,postDao)
}