package com.themoviedb.movies.api

import com.themoviedb.movies.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private lateinit var okHttpClientBuilder: OkHttpClient.Builder
    private lateinit var adapterBuilder: Retrofit.Builder
    private lateinit var retrofit: Retrofit

    private fun createDefaultAdapter() {
        okHttpClientBuilder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        okHttpClientBuilder.addInterceptor(logging)

        adapterBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

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