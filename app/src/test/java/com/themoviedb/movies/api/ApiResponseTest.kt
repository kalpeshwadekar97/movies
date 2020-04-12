package com.themoviedb.movies.api

import org.junit.After
import org.junit.Assert
import org.junit.Test

/**
 * This class is to test whether ApiResponse class is providing with the right API status
 * */
class ApiResponseTest {
    private lateinit var result: ApiResponse
    private lateinit var expected: ApiResponse

    @Test
    fun getSuccess() {
        result = ApiResponse.success(null)
        expected = ApiResponse(ApiResponseStatus.SUCCESS, null, null)
    }

    @Test
    fun getError() {
        result = ApiResponse.error(null)
        expected = ApiResponse(ApiResponseStatus.ERROR, null, null)
    }

    @Test
    fun getServerError() {
        result = ApiResponse.serverError()
        expected = ApiResponse(ApiResponseStatus.SERVER_ERROR, null, null)
    }

    @Test
    fun getLoading() {
        result = ApiResponse.loading()
        expected = ApiResponse(ApiResponseStatus.LOADING, null, null)
    }

    @Test
    fun getNoInternet() {
        result = ApiResponse.noInternet()
        expected = ApiResponse(ApiResponseStatus.NO_INTERNET, null, null)
    }

    @After
    fun checkStatus() {
        Assert.assertEquals(expected.status, result.status)
    }
}