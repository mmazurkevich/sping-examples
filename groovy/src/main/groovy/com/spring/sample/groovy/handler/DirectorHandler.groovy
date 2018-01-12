package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class DirectorHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        try {
            //get film director
            def filmDirector = document.select("span[itemprop=director]").first().text();
            println "Film director: ${filmDirector}"
            film.director = filmDirector
        } catch (Exception e) {
            println "Exception in parsing film director"
            println "$e"
        }
    }
}
