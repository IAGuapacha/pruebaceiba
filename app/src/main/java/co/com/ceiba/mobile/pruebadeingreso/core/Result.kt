package co.com.ceiba.mobile.pruebadeingreso.core

import java.lang.Exception

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure<out T>(val exception: Exception) : Result<T>()

}