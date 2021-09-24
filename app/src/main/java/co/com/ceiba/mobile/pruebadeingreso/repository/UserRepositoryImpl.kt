package co.com.ceiba.mobile.pruebadeingreso.repository

import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.local.LocalDataSource
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.model.toUserEntity
import co.com.ceiba.mobile.pruebadeingreso.data.remote.RemoteDataSource
import java.lang.Exception
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        try {
            var users = localDataSource.getUsers()
            if (users.isEmpty()){
                users = remoteDataSource.getusers()
                users.forEach { user ->
                    localDataSource.saveUser(user.toUserEntity())
                }
                return Result.Success(localDataSource.getUsers())
            }else{
                return Result.Success(users)
            }
        } catch (e: Exception) {
            return Result.Failure(e)
        }
    }
}