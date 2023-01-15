package com.viacheslav.movieguide.data

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Failure<T>(val code: Int = 0) : Result<T>()
}