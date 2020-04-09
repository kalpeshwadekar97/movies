package com.themoviedb.movies.api

import androidx.lifecycle.MutableLiveData

class ApiCallbackResponseListener(private val responseLiveData: MutableLiveData<ApiResponse>) :
    ApiCallbackResponse {
    override fun onSuccess(response: Any?) {
        responseLiveData.value = ApiResponse.success(response)
    }

    override fun onFail(message: String?) {
        responseLiveData.value = ApiResponse.error(message)
    }

    override fun onServerFail(message: String?) {
        responseLiveData.value = ApiResponse.serverError()
    }
}