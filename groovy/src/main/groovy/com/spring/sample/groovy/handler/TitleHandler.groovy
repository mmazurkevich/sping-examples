package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class TitleHandler implements Handler {

    @Override
    def handle(Document document, Film film) {
        parseFilmTitle(document, film)
        parseOriginalFilmTitle(document, film)
    }

    def parseFilmTitle(Document document, Film film) {
        try {
            //localized film title
            def title = document.select("h1[itemprop=name]").first().text()
            println "Film title: $title"
            film.title = title
        } catch (Exception e) {
            println "Exception in parsing film titles"
            println "$e"
        }
    }

    def parseOriginalFilmTitle(Document document, Film film) {
        try {
            //getting original title
            def originalTitle = document.select("div.originalTitle").first().text()
            println "Origin title: $originalTitle"
            film.originalTitle = originalTitle
        } catch (Exception e) {
            println "Exception in parsing original film titles"
            println "$e"
        }
    }
}
