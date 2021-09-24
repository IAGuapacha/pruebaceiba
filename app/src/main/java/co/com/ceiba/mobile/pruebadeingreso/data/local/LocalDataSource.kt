package co.com.ceiba.mobile.pruebadeingreso.data.local

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.PostEntity
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.model.UserEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val postDao: PostDao
) {

    suspend fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    suspend fun saveUser(user: UserEntity) {
        userDao.saveUser(user)
    }

    suspend fun getPosts(userId:Int):List<Post>{
        return postDao.getPosts(userId)
    }

    suspend fun savePost(post:PostEntity){
        postDao.savePost(post)
    }

}