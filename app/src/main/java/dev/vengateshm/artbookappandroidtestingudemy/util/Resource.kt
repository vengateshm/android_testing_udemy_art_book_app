package dev.vengateshm.artbookappandroidtestingudemy.util

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, message = null)
        }

        fun <T> failure(message: String, data: T?): Resource<T> {
            return Resource(status = Status.FAILURE, data = data, message = message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data, message = null)
        }
    }
}

enum class Status {
    SUCCESS,
    FAILURE,
    LOADING
}