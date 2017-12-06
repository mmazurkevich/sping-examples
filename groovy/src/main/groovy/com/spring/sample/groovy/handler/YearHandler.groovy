package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * Created by Mikhail on 07.12.2017.
 */
class YearHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        //get year value
        def filmYear = document.select("span#titleYear").first().text()
        println "Film year: ${filmYear}"
        film.year = filmYear
    }
}
