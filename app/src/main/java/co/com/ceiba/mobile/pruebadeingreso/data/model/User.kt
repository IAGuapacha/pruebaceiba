package co.com.ceiba.mobile.pruebadeingreso.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val id:String = "",
    val name:String = "",
    val username:String = "",
    val email:String = "",
    val phone:String = ""
)

// Room
@Entity
data class UserEntity(

    @PrimaryKey
    val id:String = "",
    @ColumnInfo(name = "name")
    val name:String = "",
    @ColumnInfo(name = "username")
    val username:String = "",
    @ColumnInfo(name = "email")
    val email:String = "",
    @ColumnInfo(name = "phone")
    val phone:String = ""
)



fun User.toUserEntity():UserEntity = UserEntity(
    this.id,
    this.name,
    this.username,
    this.email,
    this.phone
)
