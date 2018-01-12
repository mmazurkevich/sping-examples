package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * Created by Mikhail on 08.12.2017.
 */
class StarHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        try {
            //Get actors
            def stars = document.select("span[itemprop=actors]").text().split(',')
            println "Film stars: $stars"
            film.stars = stars
        } catch (Exception e) {
            println "Exception in parsing film stars"
            println "$e"
        }
    }
}
