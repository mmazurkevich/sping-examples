package com.spring.sample.groovy

import com.spring.sample.groovy.handler.RatingHandler
import com.spring.sample.groovy.handler.FilmDetailHandler
import com.spring.sample.groovy.handler.StarHandler
import com.spring.sample.groovy.handler.StorylineHandler
import com.spring.sample.groovy.handler.DirectorHandler
import com.spring.sample.groovy.handler.DurationHandler
import com.spring.sample.groovy.handler.GenreHandler
import com.spring.sample.groovy.handler.Handler
import com.spring.sample.groovy.handler.TaglineHandler
import com.spring.sample.groovy.handler.TitleHandler
import com.spring.sample.groovy.handler.WriterHandler
import com.spring.sample.groovy.handler.YearHandler
import com.spring.sample.groovy.model.Film
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.text.DecimalFormat

/**
 * Created by Mikhail on 01.12.2017.
 */
class Main {


    private static int previousFilmId = 0;

    static void main(String[] args) {
//        System.setProperty("http.proxyHost", "proxy.t-systems.ru")
//        System.setProperty("http.proxyPort", "3128")

        def films = []
        (1..9999999).each {
            def film = Film.newInstance()
            Document document = Jsoup.connect(generateNextUrl()).get()
            pageHandlers().each {
                it.handle(document, film)
            }
            println(film)
            films.add(film)
        }
    }

    static String generateNextUrl() {
        def formatter = DecimalFormat.newInstance("0000000")
        return "http://www.imdb.com/title/tt" + formatter.format(++previousFilmId) + "/"
    }

    static List<Handler> pageHandlers() {
        def pageHandlers = new ArrayList<Handler>()
        pageHandlers.add(TitleHandler.newInstance())
        pageHandlers.add(YearHandler.newInstance())
        pageHandlers.add(GenreHandler.newInstance())
        pageHandlers.add(RatingHandler.newInstance())
        pageHandlers.add(DurationHandler.newInstance())
        pageHandlers.add(DirectorHandler.newInstance())
        pageHandlers.add(StarHandler .newInstance())
        pageHandlers.add(WriterHandler .newInstance())
        pageHandlers.add(StorylineHandler.newInstance())
        pageHandlers.add(TaglineHandler.newInstance())
        pageHandlers.add(FilmDetailHandler.newInstance())
        return pageHandlers
    }
}
