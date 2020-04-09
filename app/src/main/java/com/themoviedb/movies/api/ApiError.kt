package com.themoviedb.movies.api

data class APIError(
    val status_message: String = "Something went wrong. Please try again later."
)