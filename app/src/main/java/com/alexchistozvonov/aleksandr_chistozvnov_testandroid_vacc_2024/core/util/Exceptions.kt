package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util

open class ExceptionTest : Exception()

class NetworkException : ExceptionTest()
class TimeoutException : ExceptionTest()
class GeneralException(val value: String? = "", val code: Int? = null) : ExceptionTest()
class ServerException(val errorCode: Int? = null) : ExceptionTest()
