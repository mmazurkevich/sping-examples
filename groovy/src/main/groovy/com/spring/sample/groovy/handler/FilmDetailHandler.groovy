package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Created by Mikhail on 09.12.2017.
 */
class FilmDetailHandler implements Handler{
    @Override
    def handle(Document document, Film film) {
        def detailElements = document.select("div[id*=titleDetails] > div.txt-block")
        //Get film language
        def language = detailElements.get(2).text()
        println "Film language: $language"
        film.language = language
        //Obtain release date
        def releaseDate = detailElements.get(3).text()
        println "Film release date: $releaseDate"
        film.releaseDate = releaseDate
    }
}
