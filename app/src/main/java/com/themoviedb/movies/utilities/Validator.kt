package com.themoviedb.movies.utilities

import android.text.TextUtils

object Validator {
    fun isEmpty(str: String?): Boolean {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str?.trim { it <= ' ' })
    }
}