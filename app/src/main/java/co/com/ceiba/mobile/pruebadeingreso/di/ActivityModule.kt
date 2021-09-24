package co.com.ceiba.mobile.pruebadeingreso.di

import co.com.ceiba.mobile.pruebadeingreso.BaseApplication_HiltComponents
import co.com.ceiba.mobile.pruebadeingreso.repository.PostRepository
import co.com.ceiba.mobile.pruebadeingreso.repository.PostRepositoryImpl
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepoUserImpl(repoUserImpl:UserRepositoryImpl):UserRepository

    @Binds
    abstract fun bindRepoPostImpl(repoPostImpl:PostRepositoryImpl):PostRepository
}