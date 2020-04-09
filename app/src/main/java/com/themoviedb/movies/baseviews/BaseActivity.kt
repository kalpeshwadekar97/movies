package com.themoviedb.movies.baseviews

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.themoviedb.movies.R
import com.themoviedb.movies.api.ApiResponse
import com.themoviedb.movies.api.ApiResponseStatus
import com.themoviedb.movies.customviews.CustomProgressBar
import com.themoviedb.movies.utilities.Validator
import kotlinx.android.synthetic.main.toolbar_movies.*

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressBar: CustomProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = CustomProgressBar(this)
    }

    fun showSnackBar(view: View, msg: String?) {
        if (msg != null && !Validator.isEmpty(msg)) {
            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
            snackbar.setAction(getString(R.string.lbl_okay)) {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    fun showToast(msg: String?) {
        if (msg != null && !Validator.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    open fun processApiResponse(apiResponse: ApiResponse, api: String) {
        when (apiResponse.status) {
            ApiResponseStatus.NO_INTERNET ->
                showToast(getString(R.string.no_internet_msg))
            ApiResponseStatus.LOADING ->
                progressBar.show()
            ApiResponseStatus.SUCCESS -> {
                progressBar.hide()
            }
            ApiResponseStatus.ERROR -> {
                progressBar.hide()
                showToast(apiResponse.error)
            }
            ApiResponseStatus.SERVER_ERROR -> {
                progressBar.hide()
                showToast(getString(R.string.something_went_wrong))
            }
        }
    }

    open fun setToolbar() {
        toolbar_title.text = getString(R.string.app_name)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}