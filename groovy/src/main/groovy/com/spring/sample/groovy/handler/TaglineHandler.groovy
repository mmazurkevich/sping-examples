package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * Created by Mikhail on 09.12.2017.
 */
class TaglineHandler implements Handler {
    @Override
    def handle(Document document, Film film) {
        try {
            //Get tagline
            document.select("div[id*=titleStoryLine] > div.txt-block").each {
                if (it.text().contains("Taglines")) {
                    def tagline = it.text().replace("Taglines:", "")
                    println "Film tagline: $tagline"
                    film.tagline = tagline
                }
            }
        } catch (Exception e) {
            println "Exception in parsing film tagline"
            println "$e"
        }
    }
}
