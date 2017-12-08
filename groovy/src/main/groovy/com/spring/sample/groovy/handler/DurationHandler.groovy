package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class DurationHandler implements Handler{
    @Override
    def handle(Document document, Film film) {
        //Obtain film duration
        def duration = document.select("time[itemprop=duration]").first().text()
        println "Film duration: $duration"
        film.duration = duration
    }
}
