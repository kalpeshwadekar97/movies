package com.themoviedb.movies.utilities

import com.themoviedb.movies.moviedetails.model.ProductionCountry
import com.themoviedb.movies.moviedetails.model.SpokenLanguage
import java.lang.StringBuilder

object Helper {
    fun getFormattedTimeFromMinutes(time: Int): String {
        return "${time / 60}hr ${time % 60}mins"
    }

    fun getFormattedCountryLanguageList(
        countries: List<ProductionCountry>,
        languages: List<SpokenLanguage>
    ): String {
        val countryLanguage = StringBuilder()
        for (country in countries) {
            countryLanguage.append("${country.name} | ")
        }
        for (language in languages) {
            countryLanguage.append("${language.name} | ")
        }
        return countryLanguage.toString().dropLast(2)
    }
}