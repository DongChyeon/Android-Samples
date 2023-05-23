package com.hana.composemvisample.data

sealed class ResultWrapper<out R> {

    data class Success<out T>(val data: T) : ResultWrapper<T>()

    data class Error(val errorMessage: String) : ResultWrapper<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorMessage=$errorMessage]"
        }
    }

    inline fun isSuccess(action: (R) -> Unit) {
        if (this is Success) {
            action(this.data)
        }
    }

    inline fun isError(action: (errorMessage: String) -> Unit) {
        if (this is Error) {
            action(this.errorMessage)
        }
    }

}
