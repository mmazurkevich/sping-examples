package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class StorylineHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        try {
            //Get film description
            def filmDescription = document.select("div[id*=titleStoryLine] > div[itemprop=description]").text()
            println "Film description: ${filmDescription}"
            film.storyline = filmDescription
        } catch (Exception e) {
            println "Exception in parsing film storyline"
            println "$e"
        }
    }
}
