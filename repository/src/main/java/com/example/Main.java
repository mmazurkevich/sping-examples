package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by Mikhail on 21.01.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("http://www.imdb.com/title/tt1293847/?ref_=inth_ov_tt").get();
        //get film name with year
        //TODO:: cut the year from the end of film title
        document.select("h1[itemprop=name]").first().text();
        //getting year value
        document.select("span#titleYear").first().text();
        //getting original title
        document.select("div.originalTitle").first().text();
        //getting content rating
        document.select("meta[itemprop=contentRating]").attr("content");
        //obtain film duration
        document.select("time[itemprop=duration]").first().text();
        //for each through the list of genres
        //TODO:: for each from the list
        document.select("span[itemprop=genre]").first().text();
        //get date of film publishing
        //TODO:: check got two values
        document.select("meta[itemprop=datePublished]").attr("content");
        //getting poster image
        document.select("img[itemprop=image]").first().attr("src");
        //get film description
        document.select("div[itemprop=description].summary_text").first().text();
        //get film director
        document.select("span[itemprop=director]").first().text();
        //get creators list
        //TODO:: for each check cut the begin of the line 'Writers:'
        document.select("div.credit_summary_item").get(1).text();
        //get actors list
        //TODO:: foreach iterate (split by ',')
        document.select("span[itemprop=actors]").text();

    }
}