package com.themoviedb.movies.utilities

import com.themoviedb.movies.moviedetails.model.ProductionCountry
import com.themoviedb.movies.moviedetails.model.SpokenLanguage

object Helper {
    fun getFormattedTimeFromMinutes(minutes: Int): String {
        return if (minutes > 0)
            "${minutes / 60}hr ${minutes % 60}mins"
        else
            "0hr 0mins"
    }

    fun getFormattedCountryLanguageList(
        countries: List<ProductionCountry>,
        languages: List<SpokenLanguage>
    ): String {
        val countryLanguage = StringBuilder()
        for (country in countries) {
            if (country.name.isNotEmpty()) countryLanguage.append("${country.name} | ")
        }
        for (language in languages) {
            if (language.name.isNotEmpty()) countryLanguage.append("${language.name} | ")
        }
        return countryLanguage.toString().dropLast(3)
    }
}