package com.themoviedb.movies.utilities


import com.themoviedb.movies.moviedetails.model.ProductionCountry
import com.themoviedb.movies.moviedetails.model.SpokenLanguage
import org.junit.Assert.assertEquals
import org.junit.Test

internal class HelperTest {

    @Test
    fun getFormattedTimeFromMinutes_for150Minutes() {
        val result = Helper.getFormattedTimeFromMinutes(150)
        assertEquals("2hr 30mins", result)
    }

    @Test
    fun getFormattedTimeFromMinutes_for0Minutes() {
        val result = Helper.getFormattedTimeFromMinutes(0)
        assertEquals("0hr 0mins", result)
    }

    @Test
    fun getFormattedTimeFromMinutes_forNegativeMinutes() {
        val result = Helper.getFormattedTimeFromMinutes(-1)
        assertEquals("0hr 0mins", result)
    }

    @Test
    fun getFormattedCountryLanguageList_withBothList() {
        val productionCountryList = listOf<ProductionCountry>(
            ProductionCountry("1", "India"),
            ProductionCountry("2", "USA"),
            ProductionCountry("3", "Canada")
        )
        val spokenLanguageList = listOf<SpokenLanguage>(
            SpokenLanguage("1", "Hindi"),
            SpokenLanguage("2", "English")
        )
        val result =
            Helper.getFormattedCountryLanguageList(productionCountryList, spokenLanguageList)
        val expected = "India | USA | Canada | Hindi | English"
        assertEquals(expected, result)
    }

    @Test
    fun getFormattedCountryLanguageList_withBlankCountryList() {
        val productionCountryList = listOf<ProductionCountry>()
        val spokenLanguageList = listOf<SpokenLanguage>(
            SpokenLanguage("1", "Hindi"),
            SpokenLanguage("2", "English")
        )
        val result =
            Helper.getFormattedCountryLanguageList(productionCountryList, spokenLanguageList)
        val expected = "Hindi | English"
        assertEquals(expected, result)
    }

    @Test
    fun getFormattedCountryLanguageList_withBlankLanguageList() {
        val productionCountryList = listOf<ProductionCountry>(
            ProductionCountry("1", "India"),
            ProductionCountry("2", "USA"),
            ProductionCountry("3", "Canada")
        )
        val spokenLanguageList = listOf<SpokenLanguage>()
        val result =
            Helper.getFormattedCountryLanguageList(productionCountryList, spokenLanguageList)
        val expected = "India | USA | Canada"
        assertEquals(expected, result)
    }

    @Test
    fun getFormattedCountryLanguageList_withBothBlankList() {
        val productionCountryList = listOf<ProductionCountry>()
        val spokenLanguageList = listOf<SpokenLanguage>()
        val result =
            Helper.getFormattedCountryLanguageList(productionCountryList, spokenLanguageList)
        val expected = ""
        assertEquals(expected, result)
    }

    @Test
    fun getFormattedCountryLanguageList_withBlankCountryOrLanguageName() {
        val productionCountryList = listOf<ProductionCountry>(
            ProductionCountry("1", "India"),
            ProductionCountry("2", ""),
            ProductionCountry("3", "Canada")
        )
        val spokenLanguageList = listOf<SpokenLanguage>(
            SpokenLanguage("1", ""),
            SpokenLanguage("2", "English")
        )
        val result =
            Helper.getFormattedCountryLanguageList(productionCountryList, spokenLanguageList)
        val expected = "India | Canada | English"
        assertEquals(expected, result)
    }
}