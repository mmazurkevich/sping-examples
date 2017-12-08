package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * Created by Mikhail on 09.12.2017.
 */
class TaglineHandler implements Handler{
    @Override
    def handle(Document document, Film film) {
        //Get tagline
        def tagline = document.select("div[id*=titleStoryLine] > div.txt-block").first().text()
        println "Film tagline: $tagline"
        film.tagline = tagline
    }
}
