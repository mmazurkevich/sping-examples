package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class TitleHandler implements Handler{

    @Override
    def handle(Document document, Film film) {
        //localized film title
        def title = document.select("h1[itemprop=name]").first().text()
        //getting original title
        def originalTitle = document.select("div.originalTitle").first().text()
        println("Film title: " + title + " origin title: " + originalTitle)
        film.title = title
        film.originalTitle = originalTitle
    }
}
