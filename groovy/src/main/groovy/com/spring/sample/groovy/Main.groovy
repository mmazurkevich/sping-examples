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

/**
 * Created by Mikhail on 01.12.2017.
 */
class Main {

    static void main(String[] args) {
//        System.setProperty("http.proxyHost", "proxy.t-systems.ru")
//        System.setProperty("http.proxyPort", "3128")

        def film = Film.newInstance()
        Document document = Jsoup.connect("http://www.imdb.com/title/tt1615160").get()
        pageHandlers().each {
            it.handle(document, film)
        }
        println(film)

//        //get date of film publishing
//        //TODO:: check got two values
//        document.select("meta[itemprop=datePublished]").attr("content");
//        //getting poster image
//        document.select("img[itemprop=image]").first().attr("src");
//        //get creators list
//        //TODO:: for each check cut the begin of the line 'Writers:'
//        document.select("div.credit_summary_item").get(1).text();

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
