package com.spring.sample.groovy

import com.spring.sample.groovy.handler.Handler
import com.spring.sample.groovy.handler.TitleHandler
import com.spring.sample.groovy.model.Film
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by Mikhail on 01.12.2017.
 */
class Main {

    static void main(String[] args) {
        def film = Film.newInstance()
        Document document = Jsoup.connect("http://www.imdb.com/title/tt1615160/").get()
        pageHandlers().each {
            it.handle(document, film)
        }
        println(film)

//        //getting year value
//        document.select("span#titleYear").first().text();

//        //getting content rating
//        document.select("meta[itemprop=contentRating]").attr("content");
//        //obtain film duration
//        document.select("time[itemprop=duration]").first().text();
//        //for each through the list of genres
//        //TODO:: for each from the list
//        document.select("span[itemprop=genre]").first().text();
//        //get date of film publishing
//        //TODO:: check got two values
//        document.select("meta[itemprop=datePublished]").attr("content");
//        //getting poster image
//        document.select("img[itemprop=image]").first().attr("src");
//        //get film description
//        document.select("div[itemprop=description].summary_text").first().text();
//        //get film director
//        document.select("span[itemprop=director]").first().text();
//        //get creators list
//        //TODO:: for each check cut the begin of the line 'Writers:'
//        document.select("div.credit_summary_item").get(1).text();
//        //get actors list
//        //TODO:: foreach iterate (split by ',')
//        document.select("span[itemprop=actors]").text();
    }

    static List<Handler> pageHandlers() {
        def pageHandlers = new ArrayList<Handler>()
        pageHandlers.add(TitleHandler.newInstance())
        return pageHandlers
    }
}
