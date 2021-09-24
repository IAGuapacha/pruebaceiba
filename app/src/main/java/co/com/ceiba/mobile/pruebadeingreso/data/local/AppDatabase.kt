package co.com.ceiba.mobile.pruebadeingreso.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.com.ceiba.mobile.pruebadeingreso.data.model.PostEntity
import co.com.ceiba.mobile.pruebadeingreso.data.model.UserEntity

@Database(entities = [UserEntity::class,PostEntity::class],version = 1 )
abstract class AppDatabase:RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun postDao():PostDao
}