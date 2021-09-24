package co.com.ceiba.mobile.pruebadeingreso.data.remote

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getusers():List<User> = webService.getUsers()
    suspend fun getPosts(userId:Int):List<Post> = webService.getPosts(userId)
}