package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import com.spring.sample.groovy.model.Genre
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class GenreHandler implements Handler{
    @Override
    def handle(Document document, Film film) {
        def genres = document.select("span[itemprop=genre]")
        def listOfGenres = genres.stream().collect { Genre.valueOf(it.text().toUpperCase()) }
        println "File genres: $listOfGenres"
        film.genres = listOfGenres
    }
}
