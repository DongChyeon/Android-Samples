package com.hana.composemvisample.data.exception

import com.google.gson.Gson
import com.hana.composemvisample.data.model.ErrorResult
import com.hana.composemvisample.data.util.GsonHelper
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(private val delegate: Call<T>) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(
                                response.code(),
                                Result.success(response.body()!!)
                            )
                        )
                    } else {
                        val errorResponse =
                            GsonHelper.gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResult::class.java
                            )

                        callback.onResponse(
                            this@ResultCall,
                            Response.success(
                                Result.failure(
                                    Exception(errorResponse.message)
                                )
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val errorMessage = when (t) {
                        is IOException -> "No internet connection"
                        is HttpException -> "Something went wrong!"
                        else -> t.localizedMessage
                    }
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(Result.failure(RuntimeException(errorMessage, t)))
                    )
                }
            }
        )
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun execute(): Response<Result<T>> {
        return Response.success(Result.success(delegate.execute().body()!!))
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun clone(): Call<Result<T>> {
        return ResultCall(delegate.clone())
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }

}