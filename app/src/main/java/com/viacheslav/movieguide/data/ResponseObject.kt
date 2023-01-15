package com.viacheslav.movieguide.data

sealed class ResponseObject<T> {
    class Success<T>(val data: T) : ResponseObject<T>()
    class Failure<T>(val code: Int = 0) : ResponseObject<T>()
    class Loading<T> : ResponseObject<T>()
}