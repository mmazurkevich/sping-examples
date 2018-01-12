package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Created by Mikhail on 09.12.2017.
 */
class FilmDetailHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        try {
            def detailElements = document.select("div[id*=titleDetails] > div.txt-block")
            parseFilmCountry(detailElements, film)
            parseFilmLanguage(detailElements, film)
            parseFilmReleaseDate(detailElements, film)
        } catch (Exception e) {
            println "Unexpected exception in parsing film details"
            println "$e"
        }
    }

    def parseFilmCountry(Elements detailElements, Film film) {
        try {
            //Get film country
            detailElements.each {
                if (it.text().startsWith("Country")) {
                    def country = it.text().replace("Country:", "")
                    println "Film country: $country"
                    film.country = country
                }
            }
        } catch (Exception e) {
            println "Exception in parsing film country"
            println "$e"
        }
    }

    def parseFilmLanguage(Elements detailElements, Film film) {
        try {
            //Get film language
            detailElements.each {
                if (it.text().startsWith("Language")) {
                    def language = it.text().replace("Language:", "")
                    println "Film language: $language"
                    film.language = language
                }
            }
        } catch (Exception e) {
            println "Exception in parsing film language"
            println "$e"
        }
    }

    def parseFilmReleaseDate(Elements detailElements, Film film) {
        try {
            //Obtain release date
            detailElements.each {
                if (it.text().startsWith("Release Date")) {
                    def releaseDate = it.text().replace("Release Date:", "")
                                               .replace("See more »", "")
                    println "Film release date: $releaseDate"
                    film.releaseDate = releaseDate
                }
            }
        } catch (Exception e) {
            println "Exception in parsing film release date"
            println "$e"
        }
    }
}
