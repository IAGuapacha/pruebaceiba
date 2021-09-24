package co.com.ceiba.mobile.pruebadeingreso.data.remote

import co.com.ceiba.mobile.pruebadeingreso.application.AppConstans
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {

    @GET(AppConstans.GET_USERS)
    suspend fun getUsers(): List<User>

    @GET(AppConstans.GET_POST_USER)
    suspend fun getPosts(@Query("userId")userId:Int):List<Post>
}