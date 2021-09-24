package co.com.ceiba.mobile.pruebadeingreso.repository

import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post

interface PostRepository {
    suspend fun getPosts(userId:Int): Result<List<Post>>
}