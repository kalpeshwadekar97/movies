package com.themoviedb.movies.api

class ApiResponse(var status: ApiResponseStatus, var data: Any?, var error: String?) {

    companion object{
        fun loading(): ApiResponse {
            return ApiResponse(ApiResponseStatus.LOADING, null, null)
        }

        fun success(data: Any?): ApiResponse {
            return ApiResponse(ApiResponseStatus.SUCCESS, data, null)
        }

        fun error(error: String?): ApiResponse {
            return ApiResponse(ApiResponseStatus.ERROR, null, error)
        }

        fun serverError(): ApiResponse {
            return ApiResponse(ApiResponseStatus.SERVER_ERROR, null, null)
        }

        fun noInternet(): ApiResponse {
            return ApiResponse(ApiResponseStatus.NO_INTERNET, null, null)
        }
    }
}