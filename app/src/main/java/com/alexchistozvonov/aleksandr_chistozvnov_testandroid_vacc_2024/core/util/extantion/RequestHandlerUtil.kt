package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion

import android.net.ParseException
import android.util.Log
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.ExceptionTest
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.NetworkException
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.ServerException
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.TimeoutException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

suspend fun <T : Any> runLoading(
    mapper: ErrorMapper,
    block: suspend () -> T
): LoadingResult<T> {
    return try {
        LoadingResult.Success(block())
    } catch (error: Throwable) {
        Log.e("error", "runLoading error:")
        LoadingResult.Error(mapper.mapException(error))
    }
}

sealed class LoadingResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : LoadingResult<T>()
    data class Error(val exception: Exception) : LoadingResult<Nothing>()
}

class ErrorMapper @Inject constructor() {

    fun mapException(throwable: Throwable): ExceptionTest {
        return when (throwable) {
            is Exception -> invoke(throwable)
            else -> ServerException()
        }
    }

    operator fun invoke(exception: Exception): ExceptionTest {
        return when (exception) {
            is UnknownHostException -> NetworkException()
            is SocketTimeoutException -> TimeoutException()
            is ParseException -> ServerException()
            else -> ServerException()
        }
    }
}



