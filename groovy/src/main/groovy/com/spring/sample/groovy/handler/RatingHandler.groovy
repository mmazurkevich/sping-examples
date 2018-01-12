package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * Created by Mikhail on 08.12.2017.
 */
class RatingHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        try {
            //Get content rating
            def rating = document.select("span[itemprop=contentRating]").text()
            println "Film rating: $rating"
            film.rating = rating
        } catch (Exception e) {
            println "Exception in parsing film rating"
            println "$e"
        }
    }
}
