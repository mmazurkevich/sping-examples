package com.spring.sample.groovy.handler

import com.spring.sample.groovy.model.Film
import org.jsoup.nodes.Document

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
class WriterHandler implements Handler{
    @Override
    def handle(Document document, Film film) {
        //Get writers
        def writers = document.select("div.plot_summary span[itemprop=creator]").text().split(',')
        println "Film writers: $writers"
        film.writers = writers
    }
}
