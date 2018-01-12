package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * Created by Mikhail on 07.12.2017.
 */
class YearHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        try {
            //Get year value
            def filmYear = document.select("span#titleYear").first().text()
            def year = filmYear.replace('(', '').replace(')', '')
            println "Film year: ${year}"
            film.year = year
        } catch (Exception e) {
            println "Exception in parsing film year"
            println "$e"
        }
    }
}
