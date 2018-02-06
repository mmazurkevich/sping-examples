package com.spring.sample.groovy.model

import groovy.transform.ToString

@ToString
class Film {
    String title
    String originalTitle
    String year
    String rating
    List<Genre> genres
    String director
    List<String> writers
    List<String> stars
    String duration
    String storyline
    String tagline
    String country
    String language
    String releaseDate
}
