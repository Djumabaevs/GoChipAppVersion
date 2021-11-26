package com.djumabaevs.gochipapp.login.token

import com.djumabaevs.gochipapp.R
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import javax.inject.Inject

//interface ApiCaller {
//    suspend fun <T> call(
//        request: suspend () -> T
//    ): RequestResult<T>
//
//    suspend fun <T> multiCall(
//        vararg requests: T
//    ): List<RequestResult<T>>
//
//    suspend fun <T1, T2, R> zip(
//        request1: suspend () -> T1,
//        request2: suspend () -> T2,
//        zipper: (RequestResult<T1>, RequestResult<T2>) -> R
//    ): R
//}
//
//class ApiCallerImpl @Inject constructor(
//    private val res: ResourceProvider,
//    private val userData: UserData
//) : ApiCaller {
//
//    /**
//     * Request handler for `kotlin coroutines` waiting for the request [request]
//     * handles server and connection errors
//     * returns [RequestResult.Success] or [RequestResult.Error]
//     * Applies to Retrofit-api suspend functions
//     * Clears user data when token expires
//     */
//    override suspend fun <T> call(
//        request: suspend () -> T
//    ): RequestResult<T> {
//        return try {
//            coroutineScope {
//                RequestResult.Success(request.invoke())
//            }
//        } catch (e: Exception) {
//            val requestResult = handleException(e)
//            if (requestResult.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                userData.clearUserData()
//            }
//            requestResult
//        }
//    }
//
//    /**
//     * Request handler for homogeneous requests for `kotlin coroutines` [requests],
//     * they must return same data type, runs all the requests and writes them
//     * to the [RequestResult] array.
//     * Handles server and connection errors using [call]
//     */
//    override suspend fun <T> multiCall(
//        vararg requests: T
//    ) = requests.map { call { it } }
//
//    /**
//     * Request handler for dissimilar requests for `kotlin coroutines`,
//     * runs [request1], [request2] and passes it to the [zipper] handler.
//     * Handles server and connection errors using [call]
//     */
//    override suspend fun <T1, T2, R> zip(
//        request1: suspend () -> T1,
//        request2: suspend () -> T2,
//        zipper: (RequestResult<T1>, RequestResult<T2>) -> R
//    ): R = zipper(call(request = request1), call(request = request2))
//
//    private fun handleException(
//        e: Exception,
//    ): RequestResult.Error {
//        return when (e) {
//            is JsonSyntaxException -> {
//                RequestResult.Error(res.getString(R.string.error_request_json_error))
//            }
//            is ConnectException -> {
//                RequestResult.Error(res.getString(R.string.error_request_connection_error))
//            }
//            is SocketTimeoutException -> {
//                RequestResult.Error(res.getString(R.string.error_request_timeout))
//            }
//            is HttpException -> when (e.code()) {
//                HttpURLConnection.HTTP_NOT_FOUND -> {
//                    RequestResult.Error(res.getString(R.string.error_request_http_error_404), e.code())
//                }
//                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
//                    RequestResult.Error(res.getString(R.string.error_request_http_error_500), e.code())
//                }
//                HttpURLConnection.HTTP_UNAUTHORIZED -> {
//                    RequestResult.Error(res.getString(R.string.error_request_http_error_401), e.code())
//                }
//                else -> {
//                    RequestResult.Error(res.getString(R.string.error_request_http_error_format), e.code())
//                }
//            }
//            else -> {
//                RequestResult.Error(res.getString(
//                    R.string.error_request_error_unknown,
//                    e.localizedMessage
//                ))
//            }
//        }
//    }
//}
//
//sealed class RequestResult<out T : Any?> {
//    data class Success<out T : Any?>(val result: T) : RequestResult<T>()
//    data class Error(val error: String, val code: Int = 0) : RequestResult<Nothing>()
//}