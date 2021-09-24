package co.com.ceiba.mobile.pruebadeingreso.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Post(
    val id:String = "",
    val userId:String = "",
    val title:String = "",
    val body:String = ""
)

//Room
@Entity
data class PostEntity(

    @PrimaryKey
    val id:String = "",
    @ColumnInfo(name = "userId")
    val userId:String = "",
    @ColumnInfo(name = "title")
    val title:String = "",
    @ColumnInfo(name = "body")
    val body:String = ""
)

fun Post.toPostEntity():PostEntity = PostEntity(
    this.id,
    this.userId,
    this.title,
    this.body
)

