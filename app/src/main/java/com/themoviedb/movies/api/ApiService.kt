package com.themoviedb.movies.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

object ApiService {
    private lateinit var okHttpClientBuilder: OkHttpClient.Builder
    private lateinit var adapterBuilder: Retrofit.Builder
    private lateinit var retrofit: Retrofit

    private fun createDefaultAdapter() {
        okHttpClientBuilder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level =
            /*if (BuildConfig.DEBUG) */HttpLoggingInterceptor.Level.BODY/* else HttpLoggingInterceptor.Level.NONE*/
        okHttpClientBuilder.addInterceptor(logging)

        /*val baseUrl = BuildConfig.BASE_API_URL*/

        adapterBuilder = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())

        retrofit = adapterBuilder.client(okHttpClientBuilder.build()).build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        if (!::retrofit.isInitialized)
            createDefaultAdapter()
        return retrofit.create(serviceClass)
    }

    fun parseError(response: Response<*>): APIError {
        return try {
            val converter = retrofit.responseBodyConverter<APIError>(
                APIError::class.java,
                arrayOfNulls<Annotation>(0)
            )
            converter.convert(response.errorBody()!!)!!
        } catch (e: Exception) {
            APIError()
        }
    }
}