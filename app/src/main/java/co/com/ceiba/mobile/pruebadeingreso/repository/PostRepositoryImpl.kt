package co.com.ceiba.mobile.pruebadeingreso.repository

import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.local.LocalDataSource
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.toPostEntity
import co.com.ceiba.mobile.pruebadeingreso.data.remote.RemoteDataSource
import java.lang.Exception
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): PostRepository {

    override suspend fun getPosts(userId: Int): Result<List<Post>> {
        try {
            var posts = localDataSource.getPosts(userId)
            if (posts.isEmpty()){
                posts = remoteDataSource.getPosts(userId)
                posts.forEach { post ->
                    localDataSource.savePost(post.toPostEntity())
                }
                return Result.Success(localDataSource.getPosts(userId))
            }else{
                return Result.Success(posts)
            }
        } catch (e: Exception) {
            return Result.Failure(e)
        }
    }
}