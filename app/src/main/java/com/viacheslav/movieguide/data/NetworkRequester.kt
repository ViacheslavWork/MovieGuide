package com.viacheslav.movieguide.data

import android.util.Log
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject


/**
 * Created by Viacheslav Avd on 12.01.2023
 */
private const val TAG = "NetworkRequester"

class NetworkRequester @Inject constructor() {
    suspend fun <T> makeRequest(request: suspend () -> Response<T>): Result<T> {
        return try {
            Log.d(TAG, "makeRequest: ${Thread.currentThread().name}")
            val response = request.invoke()
            Log.d(TAG, "after request: ${Thread.currentThread().name}")
            if (response.isSuccessful && response.body() != null)
                Result.Success(response.body()!!)
            else
                Result.Failure(response.code())
        } catch (e: HttpException) {
            Result.Failure(e.code())
        } catch (e: Exception) {
            Result.Failure(500)
        }
    }
}

