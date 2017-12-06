package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class DescriptionHandler implements Handler{
    @Override
    def handle(Document document, Film film) {
        //get film description
        def filmDescription = document.select("div[itemprop=description].summary_text").first().text()
        println "Film description: ${filmDescription}"
        film.description = filmDescription
    }
}
