package com.viacheslav.movieguide.data

import com.viacheslav.movieguide.core.CoDispatchers
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
class NetworkRequester @Inject constructor(private val coDispatchers: CoDispatchers) {
    suspend fun <T> makeRequest(request: suspend () -> Response<T>): Result<T> {
        return try {
            val response = request.invoke()
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

