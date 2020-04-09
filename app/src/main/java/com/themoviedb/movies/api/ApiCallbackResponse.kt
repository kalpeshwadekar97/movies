package com.themoviedb.movies.api

interface ApiCallbackResponse {
    fun onSuccess(response: Any?)
    fun onFail(message: String?)
    fun onServerFail(message: String?) {}
}